package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("리스트 추가 ( add )");
        System.out.println("리스트 삭제 ( del )");
        System.out.println("리스트 수정  ( edit )");
        System.out.println("리스트 내용 출력 ( ls )");
        System.out.println("이름 오름차순으로 정렬및 출력( ls_name_asc )");
        System.out.println("이름 내림차순으로 정렬및 출력 ( ls_name_desc )");
        System.out.println("날짜별로 정렬 및 출력 ( ls_date )");
        System.out.println("날짜별로 정렬 및 출력 ( ls_date_desc )");
        System.out.println("제목이나 내용에서 키워드 추출 및 출력 ( find <keyword> )");
        System.out.println("카테고리 검색 ( find_cate <keyword> )");
        System.out.println("카테고리 목록 출력 ( ls_cate )");
        System.out.println("중요도 순으로 출력 ( ls_star )");
        System.out.println("완료 체크 ( comp <id> )");
        System.out.println("완료된 것만 출력 ( ls_comp )");
        System.out.println("종료 (exit나 esc키)");
        System.out.println("도움말( help )");
    }
    
    public static void prompt() {
    	System.out.print("Command > ");	
    }
}
