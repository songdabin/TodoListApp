package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		int no = 0;
		for (TodoItem item : list.getList()) {
			no++;
		}
		
		String title, desc, category, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "---------- Create item Section ----------\n"
				+ "Enter the title of the item to create: ");
		
		title = sc.nextLine();
		if (list.isDuplicate(title)) {
			System.out.print("Title can't be duplicate. please change the title.");
			return;
		}
		//sc.nextLine();
		System.out.print("Enter the category: ");
		category = sc.nextLine().trim();
		
		//sc.nextLine();
		System.out.print("Enter the description: ");
		desc = sc.nextLine().trim();
		
		//sc.nextLine();
		System.out.print("Enter the due_date: ");
		due_date = sc.nextLine().trim();
		
		no++;
		
		TodoItem t = new TodoItem(title, desc, category, due_date);
		list.addItem(t);
		System.out.println("Item added.");
	}

	public static void deleteItem(TodoList l) {
		System.out.print("\n"
				+ "---------- Delete Item Section ----------\n"
				+ "Enter the number of the item to remove: ");
		
		Scanner sc = new Scanner(System.in);
		int no = sc.nextInt();
		
		if (no > l.getCount()) {
			System.out.println("Item doesn't exist.");
			return;
		}
		
		System.out.println((no) + ". " + l.getItem(no-1).toString());
		System.out.print("Do you want to delete this item? (y/n) > ");
		
		String check = sc.next();
		if (check.equals("y")) {
			l.deleteItem(l.getItem(no-1));
			System.out.println("Item deleted.");
		}
	}


	public static void updateItem(TodoList l) {
		// cnt -> 현재 번호, no -> 입력받은 번호 (찾아야하는 번호), total -> 전체번호
		Scanner sc = new Scanner(System.in);
		int no, cnt = 0;

		System.out.print("\n"
				+ "---------- Edit Item Section ----------\n"
				+ "Enter the number of the item to update: ");
		
		//String title = sc.nextLine().trim();
		no = sc.nextInt();
		
		if (l.getCount() < no) {
			System.out.println("Number doesn't exist.");
			return;
		}
		
		System.out.println((no) + ". " + l.getItem(no-1).toString());
		
		sc.nextLine();
		System.out.print("Enter the new title of the item: ");
		String new_title = sc.nextLine().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("Title can't be duplicate.");
			return;
		}
		
		//sc.nextLine();
		System.out.print("Enter the new category: ");
		String new_category = sc.nextLine().trim();
		
		//sc.nextLine();
		System.out.print("Enter the new description: ");
		String new_description = sc.nextLine().trim();
		
		//sc.nextLine();
		System.out.print("Enter the new due_date: ");
		String new_due_date = sc.nextLine().trim();
		
		for (TodoItem item : l.getList()) {
			cnt++;
			if (cnt == no) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description, new_category, new_due_date);
				l.addItem(t);
				System.out.println("Item updated.");
			}
		}

	}
	
	public static void listAll(TodoList l) {
		int cnt = 0, total = 0;
		for (TodoItem item : l.getList()) {
			total++;
		}
		System.out.println("\n"
				+ "---------- List " + total + " item ----------");
		for (TodoItem item : l.getList()) {
			cnt++;
			System.out.println(cnt + ". " + item.toString());
		}
	}
	
	public static void saveList(TodoList l, String filename) {
		try {
			Writer w = new FileWriter(filename);
			for (TodoItem item : l.getList()) {
				w.write(item.toSaveString());
			}
			w.close();
			
			System.out.println("File saved.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadList(TodoList l, String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String oneline;
			int cnt = 0;
			while((oneline = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(oneline, "##");
				String category = st.nextToken();
				String title = st.nextToken();
				String description = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				TodoItem item = new TodoItem(title, description, category, due_date);
				item.setCurrent_date(current_date);
				l.addItem(item);
				cnt++;				
			}
			br.close();
			System.out.println("Read " + cnt + " items.");
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			System.out.println("'"+ filename + "' Not found.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void findList(TodoList l, String keyword) {
		int cnt = 0;
		
		for (int i = 0; i < l.getCount(); i++) {
			if (l.getItem(i).getTitle().contains(keyword) || l.getItem(i).getDesc().contains(keyword)) {
				System.out.println((i+1) + ". " + l.getItem(i).toString());
				cnt++;
			}
		}
		System.out.println("Find "+ cnt +" items.");
	}

	public static void findCate(TodoList l, String cate_keyword) {
		int cnt = 0;
		
		for (int i = 0; i < l.getCount(); i++) {
			if (l.getItem(i).getCate().contains(cate_keyword)) {
				System.out.println((i+1) + ". " + l.getItem(i).toString());
				cnt++;
			}
		}
		System.out.println("Find "+ cnt +" items.");
	}

	public static void listCateAll(TodoList l) {
		Set<String> clist = new HashSet<String>();
		
		for (TodoItem c : l.getList()) {
			clist.add(c.getCategory());
		}
		
		Iterator it = clist.iterator();
		while (it.hasNext()) {
			String s = (String)it.next();
			System.out.print(s);
			if (it.hasNext()) System.out.print(" / ");
		}
		System.out.println("\nFind " + clist.size() + " categories.");
	}
}
