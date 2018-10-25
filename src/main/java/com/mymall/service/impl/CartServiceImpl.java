package com.mymall.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.mymall.common.Const;
import com.mymall.common.ResponseCode;
import com.mymall.common.ServerResponse;
import com.mymall.dao.CartMapper;
import com.mymall.dao.ProductMapper;
import com.mymall.pojo.Cart;
import com.mymall.pojo.Product;
import com.mymall.service.CartService;
import com.mymall.util.BigDecimalUtil;
import com.mymall.util.PropertiesUtil;
import com.mymall.vo.CartProductVo;
import com.mymall.vo.CartVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service("CartService")
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    /**
     * 添加到购物车
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    @Override
    public ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count) {
        if(productId == null || count == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDescription());
        }
        //判断购物车是否有该商品
        Cart cart = cartMapper.selectByUserIdAndProductId(userId, productId);
        if (cart == null){
            Cart insertCart = new Cart();
            insertCart.setUserId(userId);
            insertCart.setProductId(productId);
            insertCart.setQuantity(count);
            //默认勾选
            insertCart.setChecked(Const.Cart.CHECKED);
            cartMapper.insert(insertCart);
        }else {
            //已有该商品，数量相加
            count = count + cart.getQuantity();
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        //返回vo对象，此对象会多次复用，建立一个复用方法，即根据用户生成购物车vo对象
        return this.list(userId);
    }

    /**
     * 更新购物车数量
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    @Override
    public ServerResponse<CartVo> update(Integer userId, Integer productId, Integer count) {
        if(productId == null || userId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDescription());
        }
        Cart cart = cartMapper.selectByUserIdAndProductId(userId, productId);
        if(cart != null){
            cart.setQuantity(count);
            int updateCount = cartMapper.updateByPrimaryKeySelective(cart);
            if (updateCount == 0){
                return ServerResponse.createByErrorMessage("更新失败");
            }
        }
        return this.list(userId);
    }

    /**
     * 删除--多选
     * @param userId
     * @param productIds 以","分割多个id
     * @return
     */
    @Override
    public ServerResponse<CartVo> deleteByProductIds(Integer userId, String productIds) {
        List<String> productIdList = Splitter.on(",").splitToList(productIds);
        if (CollectionUtils.isEmpty(productIdList)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDescription());
        }
        cartMapper.deleteByUserIdProductIds(userId, productIdList);
        return this.list(userId);
    }

    @Override
    public ServerResponse<CartVo> list(Integer userId) {
        CartVo cartVo = this.getCartVoLimet(userId);
        return ServerResponse.createBySuccess(cartVo);
    }

    /**
     * 切换选中状态
     * @param userId
     * @param productId
     * @param status
     * @return
     */
    @Override
    public ServerResponse<CartVo> selectOrUnSelect(Integer userId, Integer productId, Integer status) {
        cartMapper.selectOrUnSelectByUserId(userId, productId, status);
        return this.list(userId);
    }

    @Override
    public ServerResponse<Integer> getCartProductCount(Integer userId) {
        int count = cartMapper.selectCartProductCount(userId);
        return ServerResponse.createBySuccess(count);
    }

    /**
     * 购物车返回对象生成
     * @param userId
     * @return
     */
    private CartVo getCartVoLimet(Integer userId){
        CartVo cartVo = new CartVo();
        List<CartProductVo> cartProductVoList = Lists.newArrayList();
        BigDecimal totalPrice = new BigDecimal("0.0");
        //取出该用户所有购物车条目Cart(userId, productId),组装CartProductVo也即在购物车中的同一个商品信息
        List<Cart> cartList = cartMapper.selectByUserId(userId);
        if (CollectionUtils.isNotEmpty(cartList)){
            for (Cart cart : cartList){
                CartProductVo cartProductVo = new CartProductVo();
                cartProductVo.setId(cart.getId());
                cartProductVo.setUserId(userId);
                cartProductVo.setProductId(cart.getProductId());
                cartProductVo.setProductChecked(cart.getChecked());
                cartProductVo.setProductTotalPrice(totalPrice);//先设置个默认值，防止空指针

                Product product = productMapper.selectByPrimaryKey(cart.getProductId());
                if (product != null){
                    cartProductVo.setProductMainImage(product.getMainImage());
                    cartProductVo.setProductName(product.getName());
                    cartProductVo.setProductSubtitle(product.getSubtitle());
                    cartProductVo.setProductStatus(product.getStatus());
                    cartProductVo.setProductPrice(product.getPrice());
                    cartProductVo.setProductStock(product.getStock());//库存

                    int buyLimitCount = 0;//最终的数量
                    //判断库存是否足够
                    if (product.getStock() >= cart.getQuantity()){
                        buyLimitCount = cart.getQuantity();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);//库存足够
                    }else {
                        buyLimitCount = product.getStock();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);//库存不够购物车中的数量，自动调整为库存数量
                        //检测到库存不足时，修改购物车中的数量
                        Cart updateStockCart = new Cart();
                        updateStockCart.setId(cart.getId());
                        updateStockCart.setQuantity(buyLimitCount);
                        cartMapper.updateByPrimaryKeySelective(updateStockCart);
                    }
                    cartProductVo.setQuantity(buyLimitCount);
                    //计算该商品总价，利用封装好的BigDecimalUtil工具类
                    cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), cartProductVo.getQuantity()));
                    cartProductVoList.add(cartProductVo);
                }
            }
        }
        cartVo.setCartTotalPrice(totalPrice);
        cartVo.setCartProductVoList(cartProductVoList);
        //是否全选
        cartVo.setAllChecked(this.getAllCheckedStatus(userId));
        cartVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        return cartVo;
    }

    /**
     * 是否全选
     * @param userId
     * @return
     */
    private boolean getAllCheckedStatus(Integer userId){
        if(userId == null){
            return false;
        }
        return cartMapper.selectUnCheckedStatusCountByUserId(userId) == 0;
    }

}
