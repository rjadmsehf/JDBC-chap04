package com.ohgiraffers.section01.model.dao;

import com.ohgiraffers.section01.model.dto.MenuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

import static com.ohgiraffers.common.JDBCTemplate.close;

/* 필기.
 *   DAO(DataBase Access Object)
 *   데이터 베이스 접근용 객체
 *   => CRUD 연산을 담당하는 메소드들의 집합으로 이루어진 클래스
 *  */
public class MenuDAO {

    // 1. query 문을 읽어야 됨
    private Properties prop = new Properties();

    public MenuDAO() {

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public int selectLastMenuCode(Connection con) {

//        Connection con = getConnection();
        Statement stmt = null;
        ResultSet rset = null;

        int maxMenuCode = 0;

        String query = prop.getProperty("selectLastMenuCode");

        try {
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);

            if(rset.next()) {
                maxMenuCode = rset.getInt("MAX(MENU_CODE)");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset);
            close(stmt);
        }

        return maxMenuCode;
    }

    public List<Map<Integer, String>> selectAllCategory(Connection con) {

        Statement stmt = null;
        ResultSet rset = null;

        List<Map<Integer, String>> categoryList = null;

        String query = prop.getProperty("selectAllCategoryList");

        try {
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);

            categoryList = new ArrayList<>();

            while (rset.next()) {
                Map<Integer, String> category = new HashMap<>();
                category.put(rset.getInt("CATEGORY_CODE"), rset.getString("CATEGORY_NAME"));

                categoryList.add(category);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

            close(rset);
            close(stmt);
        }

        return categoryList;

    }

    public int insertNewMenu(Connection con, MenuDTO newMenu) {

        PreparedStatement pstmt = null;

        int result = 0;

        String query = prop.getProperty("insertMenu");

        try {
            pstmt = con.prepareStatement(query);

            pstmt.setInt(1, newMenu.getMenuCode());
            pstmt.setString(2, newMenu.getMenuName());
            pstmt.setInt(3, newMenu.getMenuPrice());
            pstmt.setInt(4, newMenu.getCategoryCode());
            pstmt.setString(5, newMenu.getOrderableStatus());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
        }

        return result;
    }
}