package com.em.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/10/27 0027 16:18
 */
public class GoodsDetailsEntity implements Serializable {
        /*
            1. 商品的主图            masterImg
            2. 商品名字              name1
            3. 商品价格              marketPrice
            4. 返佣比例
            5. 推广收益
            6. 商品多张图组合
            7. 商品id                productId
            8. 标识字段
         */
        private Commodity commodity;
        private List<String> listImg;

        public Commodity getCommodity() {
                return commodity;
        }

        public void setCommodity(Commodity commodity) {
                this.commodity = commodity;
        }

        public List<String> getListImg() {
                return listImg;
        }

        public void setListImg(List<String> listImg) {
                this.listImg = listImg;
        }

}
