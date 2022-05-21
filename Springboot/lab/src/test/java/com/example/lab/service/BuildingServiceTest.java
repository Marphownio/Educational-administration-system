package com.example.lab.service;

import com.example.lab.pojo.entity.Building;
import com.example.lab.pojo.enums.ResultMessage;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class BuildingServiceTest {

    @Autowired
    BuildingService buildingService;

    @Test
    void test01AddBuilding() {
        Building building1 = new Building();
        building1.setBuildingId(1);
        building1.setBuildingName("building_1");
        assertSame(ResultMessage.SUCCESS, buildingService.addBuilding(building1));
        Building building2 = new Building();
        building2.setBuildingId(2);
        building2.setBuildingName("暂无");
        assertSame(ResultMessage.SUCCESS, buildingService.addBuilding(building2));
    }

    @Test
    void test02UpdateBuilding() {
        Building building = new Building();
        building.setBuildingId(2);
        building.setBuildingName("building_2");
        assertSame(ResultMessage.SUCCESS, buildingService.updateBuilding(building));
    }

    @Test
    void test03FindAllBuilding() {
        List<Building> buildingList = buildingService.findAllBuilding();
        assertNotNull(buildingList);
        assertEquals(2, buildingList.size());
        assertEquals(1, buildingList.get(0).getBuildingId());
        assertEquals("building_1", buildingList.get(0).getBuildingName());
        assertEquals(2, buildingList.get(1).getBuildingId());
        assertEquals("building_2", buildingList.get(1).getBuildingName());
    }

    @Test
    void test04FindBuildingById() {
        Building building = buildingService.findBuildingById(1);
        assertNotNull(building);
        assertEquals(1, building.getBuildingId());
        assertEquals("building_1", building.getBuildingName());
    }

    @Test
    void test05DeleteBuilding() {
        assertSame(ResultMessage.SUCCESS, buildingService.deleteBuilding(2));
    }
}
