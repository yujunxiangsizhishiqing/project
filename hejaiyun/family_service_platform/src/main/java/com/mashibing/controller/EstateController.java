package com.mashibing.controller;

import com.alibaba.fastjson.JSONObject;
import com.mashibing.bean.*;
import com.mashibing.returnJson.ReturnObject;
import com.mashibing.service.EstateService;
import com.mashibing.vo.CellMessage;
import com.mashibing.vo.UnitMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*",methods = {},allowCredentials = "true")
public class EstateController {

    @Autowired
    private EstateService estateService;

    @RequestMapping("/estate/selectCompany")
    public String selectCompany(){
        System.out.println("selectCompany");
        List<TblCompany> tblCompanies = estateService.selectCompany();
        return JSONObject.toJSONString(new ReturnObject(tblCompanies));
    }

    @RequestMapping("/estate/insertEstate")
    public String insertEstate(FcEstate fcEstate){
        System.out.println("insert estate");
        Integer integer = estateService.insertEstate(fcEstate);
        if (integer==0){
            return JSONObject.toJSONString(new ReturnObject("房产编码已存在","0"));
        }else{
            System.out.println("result: "+integer);
            return JSONObject.toJSONString(new ReturnObject("插入房产成功","1"));
        }
    }

    /**
     * 此处应该完成的是楼宇的查询功能，但是大家会发现，现在数据表中没有任何楼宇的数据，
     * 因此再辨析的时候需要进行插入且返回插入的数据
     * @param buildingNumber
     * @param estateCode
     * @return
     */
    @RequestMapping("/estate/selectBuilding")
    public String selectBuilding(Integer buildingNumber,String estateCode){
        System.out.println("select building");
        List<FcBuilding> fcBuildings = estateService.selectBuilding(buildingNumber, estateCode);
        System.out.println(fcBuildings);
        return JSONObject.toJSONString(new ReturnObject(fcBuildings));
    }

    @RequestMapping("/estate/updateBuilding")
    public String updateBuilding(FcBuilding fcBuilding){
        System.out.println("updateBuilding");
        Integer result = estateService.updateBuilding(fcBuilding);
        if (result == 1){
            return JSONObject.toJSONString(new ReturnObject("更新楼宇成功"));
        }else{
            return JSONObject.toJSONString(new ReturnObject("更新楼宇失败"));
        }
    }

    @RequestMapping("/estate/selectUnit")
    public String selectUnit(@RequestBody UnitMessage[] unitMessages){
        System.out.println("estate selectUnit");
        List<FcUnit> allUnit = new ArrayList<>();
        for (UnitMessage unitMessage : unitMessages) {
            allUnit.addAll(estateService.selectUnit(unitMessage));
        }
        return JSONObject.toJSONString(new ReturnObject(allUnit));
    }

    @RequestMapping("/estate/updateUnit")
    public String updateUnit(FcUnit fcUnit){
        Integer result = estateService.updateUnit(fcUnit);
        if (result==1){
            return JSONObject.toJSONString(new ReturnObject("更新单元成功"));
        }else{
            return JSONObject.toJSONString(new ReturnObject("更新单元失败"));
        }
    }

    @RequestMapping("/estate/insertCell")
    public String insertCell(@RequestBody CellMessage[] cellMessages){
        System.out.println("insert cell");
        List<FcCell> fcCells = estateService.insertCell(cellMessages);
        return JSONObject.toJSONString(new ReturnObject(fcCells));
    }

    @RequestMapping("/estate/selectBuildingByEstate")
    public String selectBuildingByEstate(String estateCode){
        System.out.println("estateCode: "+estateCode);
        List<FcBuilding> fcBuildings = estateService.selectBuildingByEstate(estateCode);
        System.out.println("---------"+fcBuildings);
        return JSONObject.toJSONString(new ReturnObject(fcBuildings));
    }

    @RequestMapping("/estate/selectUnitByBuildingCode")
    public String selectUnitByBuildingCode(String buildingCode){
        System.out.println("select unit by building code");
        List<FcUnit> fcUnits = estateService.selectUnitByBuildingCode(buildingCode);
        System.out.println("~~~~~~~~~~"+fcUnits.size());
        return JSONObject.toJSONString(new ReturnObject(fcUnits));
    }

    @RequestMapping("/estate/selectCell")
    public String selectCell(String unitCode){
        System.out.println("select cell");
        List<FcCell> fcCells = estateService.selectCell(unitCode);
        System.out.println("-----------------");
        System.out.println(fcCells.size());
        return JSONObject.toJSONString(new ReturnObject(fcCells));

    }
}
