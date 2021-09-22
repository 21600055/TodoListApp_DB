package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("1. 리스트 추가 ( add )");
        System.out.println("2. 리스트 삭제 ( del )");
        System.out.println("3. 리스트 수정  ( edit )");
        System.out.println("4. 리스트 내용 출력 ( ls )");
        System.out.println("5. 이름 오름차순으로 정렬및 출력( ls_name_asc )");
        System.out.println("6. 이름 내리차순으로 정렬및 출력 ( ls_name_desc )");
        System.out.println("7. 날짜별로 정렬 및 출력 ( ls_date )");
        System.out.println("8. 종료 (exit나 esc키)");
        System.out.println("9. 도움말( help )");
    }
    
    public static void prompt() {
    	System.out.print("Command > ");
    }
}
