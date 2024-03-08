package com.ohgiraffers.section01.run;

import com.ohgiraffers.section01.model.dao.MenuDAO;
import com.ohgiraffers.section01.model.dto.MenuDTO;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application {

    public static void main(String[] args) {

        Connection con = getConnection();
        MenuDAO registDAO = new MenuDAO();

        /* 필기. 1. 메뉴의 마지막 번호 조회 */

        int maxMenuCode = registDAO.selectLastMenuCode(con);

        System.out.println("maxMenuCode = " + maxMenuCode);

        /* 필기. 2. 카테고리 조회 */

        List<Map<Integer, String>> categoryList = registDAO.selectAllCategory(con);

        for(Map<Integer, String> category : categoryList) {
            System.out.println("category = " + category);
        }

        /* 필기. 3. 신규 메뉴 등록 */

        Scanner sc = new Scanner(System.in);

        System.out.print("등록 메뉴의 이름을 입력해주세요 : ");
        String menuName = sc.nextLine();
        System.out.print("등록 메뉴의 가격을 입력해주세요 : ");
        int menuPrice = sc.nextInt();
        System.out.print("등록 메뉴의 카테고리를 입력해주시요 : ");
        sc.nextLine();
        String categoryName = sc.nextLine();
        System.out.print("판매 메뉴에 등록하시겠습니까?(예/아니오) :");
        String answer = sc.nextLine();

        int menuCode = maxMenuCode + 1;

        int categoryCode = 0;
        switch (categoryName) {
            case "식사" : categoryCode = 1; break;
            case "음료" : categoryCode = 2; break;
            case "디저트" : categoryCode = 3; break;
            case "한식" : categoryCode = 4; break;
            case "퓨전" : categoryCode = 5; break;
        }

        String orderableStatus = "";
        switch (answer) {
            case "예" : orderableStatus = "Y"; break;
            case "아니오" : orderableStatus = "N"; break;
        }

        MenuDTO newMenu = new MenuDTO(menuCode, menuName, menuPrice, categoryCode, orderableStatus);

        int result = registDAO.insertNewMenu(con, newMenu);

        if(result > 0) {
            System.out.println("신규 메뉴 등록 성공!!!!");
        } else {
            System.out.println("신규 메뉴 등록 실패 ...");
        }

    }

}