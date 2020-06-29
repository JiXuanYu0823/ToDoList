import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

/*交互平台类
*   该程序实现了：一、用户注册、登录功能
*                二、用户信息存储，再加载功能
*                三、待办事件添加、删除、状态改变、事件列表展示等功能*/
public class TestDemo {
	public static void main(String[] args) throws IOException{
		Scanner in = new Scanner(System.in);
		//一、存储用户信息以导入导出至信息存储系统，允许多次执行程序时，调用用户信息。
		//二、允许单次执行程序时，切换用户。
		HashMap<String,Log> map = new HashMap<>();
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("C:\\idea\\Idea Pro\\src\\com" +
				"\\yujixuan\\homework\\stream.txt"))){
			map = (HashMap<String, Log>) ois.readObject();
		} catch (Exception e){
			e.printStackTrace();
		}
		//根据需求，在单次执行时，切换登录用户
		boolean flag = init(in, map);
		while(flag) {
			flag = init(in, map);
		}
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("C:\\idea\\Idea Pro\\src\\com" +
				"\\yujixuan\\homework\\stream.txt"))) {
			oos.writeObject(map);
			oos.writeObject(null);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	//初始化函数，用户登录程序并在登录成功后进入功能函数
	public static boolean init(Scanner in, HashMap<String, Log> map) {
		Log log;
		System.out.println("欢迎使用 MyToDoList");
		while(true) {
			System.out.println("注册请输入（1） 登录请输入（2）");
			int temp = in.nextInt();
			if(temp == 1){
				log = register(in,map);
				break;
			}
			else if(temp == 2){
				log = login(in,map);
				break;
			}
			else{
				System.out.println("输入错误，请重新选择");
			}
		}
		if(log == null){
			return true;
		}
		int k = start(in, log);
		if(k == 1){
			return true;
		}
		return false;
	}
	//登录函数，供初始化调用
	public static Log login(Scanner in, HashMap<String, Log> map) {
		String temp = in.nextLine();
		System.out.println("请输入您的账号：");
		String userName = in.nextLine();
		System.out.println("请输入您的密码：");
		String password = in.nextLine();
		Log log = map.get(userName);
		if(log == null){
			System.out.println("账号输入错误,请重新选择");
			return null;
		}
		else if(log.getPassword().compareTo(password) != 0) {
			System.out.println("密码输入有误，请重新选择");
			return null;
		}
		else{
			return log;
		}
	}
	//注册函数，供初始化调用
	public static Log register(Scanner in, HashMap<String, Log> map) {
		String temp = in.nextLine();
		System.out.println("请创建您的账户：");
		String userName = in.nextLine();
		System.out.println("请输入您的密码：");
		String password = in.nextLine();
		Log log = new Log(userName,password);
		map.put(userName,log);
		return log;
	}
	//功能函数，实现与用户交互
	public static int start(Scanner in, Log log) {
		log.showEvents();
		while(true){
			System.out.println("\n可选功能如下：");
			System.out.println("添加事项请输入(1)  删除事项请输入(2)  改变事项状态请输入(3)" +
					"  切换用户(4)  退出记事本(5)");
			int order = in.nextInt();
			if (order == 1) {
				String temp = in.nextLine();
				System.out.println("请输入您的事件：");
				String event = in.nextLine();
				System.out.println("请输入事件的截止日期：");
				String ddl = in.nextLine();
				log.addEvent();
			}
			else if (order == 2) {
				System.out.println("请输入事项序列号：");
				int index = in.nextInt();
				if(index > log.countEvents());
				else
					log.delEvent(index - 1);
			}
			else if (order == 3) {
				System.out.println("请输入事件序列号：");
				int index = in.nextInt();
				if(index > log.countEvents());
				else {
					System.out.println("请输入您想变更状态至：(\"未完成！\"（1）  \"已完成！\"（2）\")");
					int sta = in.nextInt();
					if (sta == 1)
						log.chanStatus();
					else if (sta == 2)
						log.chanStatus();
				}
			}
			else if(order == 4){
				return 1;
			}
			else if(order == 5){
				return 0;
			}
			else;
			log.showEvents();
		}
	}
}
