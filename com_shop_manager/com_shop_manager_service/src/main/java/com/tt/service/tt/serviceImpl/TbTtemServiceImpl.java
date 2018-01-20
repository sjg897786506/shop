package com.tt.service.tt.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tt.mapper.TbItemMapper;
import com.tt.pojo.TbItem;
import com.tt.pojo.TbItemExample;
import com.tt.service.TbItemService;
import com.tt.util.EasyUIDateGrid;
import com.tt.util.IDUtils;
import com.tt.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TbTtemServiceImpl implements TbItemService {


    @Autowired
    private TbItemMapper tbItemMapper;


    @Override
    public TbItem getItremById(Long itemid) {
//根据主键查询
//        TbItem tbItem=tbItemMapper.selectByPrimaryKey(itemid);
//可以引入条件
        TbItemExample tbItemCatExample = new TbItemExample();
        TbItemExample.Criteria tic = tbItemCatExample.createCriteria();
        tic.andIdEqualTo(itemid);
        List<TbItem> list = tbItemMapper.selectByExample(tbItemCatExample);
        if (list != null & list.size() > 0) {
            TbItem item = list.get(0);
            return item;
        }
        return null;
    }

    @Override
    public EasyUIDateGrid getItemList(int page, int rows) {

        //查询商品列表
        TbItemExample example = new TbItemExample();
        //分页处理
        PageHelper.startPage(page, rows);
        List<TbItem> list = tbItemMapper.selectByExample(example);
        //创建一个返回值对象
        EasyUIDateGrid easyUIDateGrid = new EasyUIDateGrid();
        //将拿到的集合放到easyUIDateGrid中的list中
        easyUIDateGrid.setRows(list);
        //取记录的总条数
        PageInfo<TbItem> info = new PageInfo<TbItem>(list);
        easyUIDateGrid.setTotal(info.getTotal());
        return easyUIDateGrid;
    }

    @Override
    public TaotaoResult createItem(TbItem item) {
//item补全
        Long id = IDUtils.genItemId();
        item.setId(id);
        //'商品状态，1-正常，2-下架，3-删除',
        item.setStatus((byte) 1);
        //创建时间和更新时间
        item.setCreated(new Date());
        item.setUpdated(new Date());
        //插入到数据库表
        tbItemMapper.insert(item);
        return TaotaoResult.ok();
    }
}
