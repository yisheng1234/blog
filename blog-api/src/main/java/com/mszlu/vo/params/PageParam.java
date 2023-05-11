package com.mszlu.vo.params;

import lombok.Data;
//VO:value object值对象。通常用于业务层之间的数据传递，和PO一样也是仅仅包含数据而已。
// 但应是抽象出的业务对象,可以和表对应,也可以不,这根据业务的需要.
@Data
public class  PageParam {
    //当前是第几页
    private int page =1;
    //每页的文章数量
    private int pageSize=10;

    private Long categoryId;
    private Long tagId;
    private String year;
    private String month;

    //后面用到的sql语句要求这种格式
    public String getMonth(){
        if(this.month!=null && this.month.length()==1){
            return "0"+this.month;
        }
        return this.month;
    }


}
