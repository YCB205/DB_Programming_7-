package com.Sales_manage.Sales_manage.orderSheet.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class MerchandiseRequest {

    private Long idOrdersheet;
    private Long profit;
    private Long sales;
    private List<Merchandise> merchandise;




    @Getter
    @Setter
    public static class Merchandise {
        private Long id_merchandise;
        private short orderCount;
        private Long cost;
        private Long price;




        public Long getId_merchandise() {
            return id_merchandise;
        }

        public short getOrderCount() {
            return orderCount;
        }
        public Long getTotalCost() {
            return cost * orderCount;
        }
        public Long getSales() {
            return price * orderCount;
        }

//        public void sout(){
//            System.out.println(id_merchandise);
//            System.out.println(orderCount);
//            System.out.println(cost);
//            System.out.println(price);
//        }

    }

    public Long getIdOrdersheet() {return idOrdersheet;}

    public List<Merchandise> getMerchandise() {
        return merchandise;
    }


    public void setIdOrdersheet(Long idOrdersheet) {this.idOrdersheet = idOrdersheet;}

    public void setMerchandise(List<Merchandise> merchandise) {
        this.merchandise = merchandise;
    }
}