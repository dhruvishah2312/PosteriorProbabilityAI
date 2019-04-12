import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class bnet {
	
	static Hashtable<Character, node> nodeHashTable;
	static ArrayList<String> fulfilled;
	
	private static float calcProbability(String s,ArrayList<String> list){
		float ans1=1;
		node nodeInd = nodeHashTable.get(s.charAt(0));
//		System.out.println("Processing Node : "+s+" "+mNode.name);
		char truthValue = s.charAt(1);
		if(truthValue == 't'){
			if(nodeInd.parent == null )
				ans1 = nodeInd.prob[0];
			else if(nodeInd.parent.length() == 1){
				if(list.contains(nodeInd.parent+"t")){
					ans1=nodeInd.prob[0];
					if (!fulfilled.contains(nodeInd.parent+"t")) {
						ans1 *= calcProbability(nodeInd.parent + "t", list);
						fulfilled.add(nodeInd.parent + "t");
					}
				}
				else if(list.contains(nodeInd.parent+"f")){
					ans1=nodeInd.prob[1];
					if (!fulfilled.contains(nodeInd.parent+"f")) {
						ans1 *= calcProbability(nodeInd.parent + "f", list);
						fulfilled.add(nodeInd.parent + "f");
					}
				}else{
					float ans2 = nodeInd.prob[1];
					ans1 = nodeInd.prob[0];
					ans1*=calcProbability(nodeInd.parent+"t",list);
					ans2*= calcProbability(nodeInd.parent+"f",list);
					ans1+=ans2;
				}
			}else if(nodeInd.parent.length() == 2){
				if(list.contains(nodeInd.parent.charAt(0)+"t") && list.contains(nodeInd.parent.charAt(1)+"t")){
					ans1=nodeInd.prob[0];
					if (fulfilled.contains(nodeInd.parent.charAt(0) + "t")) {
						ans1 *= calcProbability(nodeInd.parent.charAt(0) + "t", list);
						fulfilled.add(nodeInd.parent.charAt(0) + "t");
					}
					if (fulfilled.contains(nodeInd.parent.charAt(1) + "t")) {
						ans1 *= calcProbability(nodeInd.parent.charAt(1) + "t", list);
						fulfilled.add(nodeInd.parent.charAt(1) + "t");
					}
				}else if(list.contains(nodeInd.parent.charAt(0)+"t") && list.contains(nodeInd.parent.charAt(1)+"f")){
					ans1=nodeInd.prob[1];
					if (fulfilled.contains(nodeInd.parent.charAt(0) + "t")) {
						ans1 *= calcProbability(nodeInd.parent.charAt(0) + "t", list);
						fulfilled.add(nodeInd.parent.charAt(0) + "t");
					}
					if (fulfilled.contains(nodeInd.parent.charAt(1) + "f")) {
						ans1 *= calcProbability(nodeInd.parent.charAt(1) + "f", list);
						fulfilled.add(nodeInd.parent.charAt(1) + "f");
					}
				}else if(list.contains(nodeInd.parent.charAt(0)+"f") && list.contains(nodeInd.parent.charAt(1)+"t")){
					ans1=nodeInd.prob[2];
					if (fulfilled.contains(nodeInd.parent.charAt(0) + "f")) {
						ans1 *= calcProbability(nodeInd.parent.charAt(0) + "f", list);
						fulfilled.add(nodeInd.parent.charAt(0) + "f");
					}
					if (fulfilled.contains(nodeInd.parent.charAt(1) + "t")) {
						ans1 *= calcProbability(nodeInd.parent.charAt(1) + "t", list);
						fulfilled.add(nodeInd.parent.charAt(1) + "t");
					}
				}else if(list.contains(nodeInd.parent.charAt(0)+"f") && list.contains(nodeInd.parent.charAt(1)+"f")){
					ans1=nodeInd.prob[3];
					if (fulfilled.contains(nodeInd.parent.charAt(0) + "f")) {
						ans1 *= calcProbability(nodeInd.parent.charAt(0) + "f", list);
						fulfilled.add(nodeInd.parent.charAt(0) + "f");
					}
					if (fulfilled.contains(nodeInd.parent.charAt(1) + "f")) {
						ans1 *= calcProbability(nodeInd.parent.charAt(1) + "f", list);
						fulfilled.add(nodeInd.parent.charAt(1) + "f");
					}
				}else{
					float ans2 = nodeInd.prob[1], ans3 = nodeInd.prob[2], ans4 = nodeInd.prob[3];
					ans1=nodeInd.prob[0];
					ans1*= calcProbability(nodeInd.parent.charAt(0)+"t",list);
					ans1*=calcProbability(nodeInd.parent.charAt(1)+"t",list);
					
					ans2*= calcProbability(nodeInd.parent.charAt(0)+"t",list);
					ans2*=calcProbability(nodeInd.parent.charAt(1)+"f",list);

					ans3*= calcProbability(nodeInd.parent.charAt(0)+"f",list);
					ans3*=calcProbability(nodeInd.parent.charAt(1)+"t",list);

					ans4*= calcProbability(nodeInd.parent.charAt(0)+"f",list);
					ans4*=calcProbability(nodeInd.parent.charAt(1)+"f",list);
					
					ans1+=ans2+ans3+ans4;
				}
			}
		}else{
			if(nodeInd.parent == null )
				ans1 = (1-nodeInd.prob[0]);
			else if(nodeInd.parent.length() == 1){
				if(list.contains(nodeInd.parent+"t")){
					ans1=nodeInd.prob[0];
					if (!fulfilled.contains(nodeInd.parent+"t")) {
						ans1 *= calcProbability(nodeInd.parent + "t", list);
						fulfilled.add(nodeInd.parent + "t");
					}
				}
				else if(list.contains(nodeInd.parent+"f")){
					ans1=(1-nodeInd.prob[1]);
					if (!fulfilled.contains(nodeInd.parent+"f")) {
						ans1 *= calcProbability(nodeInd.parent + "f", list);
						fulfilled.add(nodeInd.parent + "f");
					}
				}else{
					float ans2 = (1-nodeInd.prob[1]);
					ans1 = (1-nodeInd.prob[0]);
					ans1*=calcProbability(nodeInd.parent+"t",list);
					ans2*= calcProbability(nodeInd.parent+"f",list);
					ans1+=ans2;
				}
			}else if(nodeInd.parent.length() == 2){
				if(list.contains(nodeInd.parent.charAt(0)+"t") && list.contains(nodeInd.parent.charAt(1)+"t")){
					ans1=(1-nodeInd.prob[0]);
					if (fulfilled.contains(nodeInd.parent.charAt(0) + "t")) {
						ans1 *= calcProbability(nodeInd.parent.charAt(0) + "t", list);
						fulfilled.add(nodeInd.parent.charAt(0) + "t");
					}
					if (fulfilled.contains(nodeInd.parent.charAt(1) + "t")) {
						ans1 *= calcProbability(nodeInd.parent.charAt(1) + "t", list);
						fulfilled.add(nodeInd.parent.charAt(1) + "t");
					}
				}else if(list.contains(nodeInd.parent.charAt(0)+"t") && list.contains(nodeInd.parent.charAt(1)+"f")){
					ans1=(1-nodeInd.prob[1]);
					if (fulfilled.contains(nodeInd.parent.charAt(0) + "t")) {
						ans1 *= calcProbability(nodeInd.parent.charAt(0) + "t", list);
						fulfilled.add(nodeInd.parent.charAt(0) + "t");
					}
					if (fulfilled.contains(nodeInd.parent.charAt(1) + "f")) {
						ans1 *= calcProbability(nodeInd.parent.charAt(1) + "f", list);
						fulfilled.add(nodeInd.parent.charAt(1) + "f");
					}
				}else if(list.contains(nodeInd.parent.charAt(0)+"f") && list.contains(nodeInd.parent.charAt(1)+"t")){
					ans1=(1-nodeInd.prob[2]);
					if (fulfilled.contains(nodeInd.parent.charAt(0) + "f")) {
						ans1 *= calcProbability(nodeInd.parent.charAt(0) + "f", list);
						fulfilled.add(nodeInd.parent.charAt(0) + "f");
					}
					if (fulfilled.contains(nodeInd.parent.charAt(1) + "t")) {
						ans1 *= calcProbability(nodeInd.parent.charAt(1) + "t", list);
						fulfilled.add(nodeInd.parent.charAt(1) + "t");
					}
				}else if(list.contains(nodeInd.parent.charAt(0)+"f") && list.contains(nodeInd.parent.charAt(1)+"f")){
					ans1=(1-nodeInd.prob[3]);
					if (fulfilled.contains(nodeInd.parent.charAt(0) + "f")) {
						ans1 *= calcProbability(nodeInd.parent.charAt(0) + "f", list);
						fulfilled.add(nodeInd.parent.charAt(0) + "f");
					}
					if (fulfilled.contains(nodeInd.parent.charAt(1) + "f")) {
						ans1 *= calcProbability(nodeInd.parent.charAt(1) + "f", list);
						fulfilled.add(nodeInd.parent.charAt(1) + "f");
					}
				}else{
					float ans2 = (1-nodeInd.prob[1]),ans3 = (1-nodeInd.prob[2]),ans4 = (1-nodeInd.prob[3]);
					ans1=(1-nodeInd.prob[0]);
					ans1*= calcProbability(nodeInd.parent.charAt(0)+"t",list);
					ans1*=calcProbability(nodeInd.parent.charAt(1)+"t",list);
					
					ans2*= calcProbability(nodeInd.parent.charAt(0)+"t",list);
					ans2*=calcProbability(nodeInd.parent.charAt(1)+"f",list);
					
					ans3*= calcProbability(nodeInd.parent.charAt(0)+"f",list);
					ans3*=calcProbability(nodeInd.parent.charAt(1)+"t",list);

					ans4*= calcProbability(nodeInd.parent.charAt(0)+"f",list);
					ans4*=calcProbability(nodeInd.parent.charAt(1)+"f",list);
					
					ans1+=ans2+ans3+ans4;
				}
			}
		}
		return ans1;
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File input = new File("input.txt");
		Scanner sc = null;
		int x,y,length;
		String ch,s;
		node nodeInd;
		ArrayList<String> calc;
		ArrayList<String> given;
		
		nodeHashTable = new Hashtable<Character,node>();
		x=0;
		y=0;
		calc = new ArrayList<String>();
		given = null;
		while(x<args.length){
			if(args[x].equals("given"))
				break;
			calc.add(args[x]);
			x++;
		}
		if(x<args.length && args[x].equals("given")){
			given = new ArrayList<String>();
			x++;
			while(x<args.length){
				given.add(args[x]);
				calc.add(args[x]);
				x++;
			}
		}
		try {
			sc= new Scanner(input);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Invalid File Path");
			e.printStackTrace();
			return;
		}
		while(sc.hasNextLine()){
			ch = sc.next();
//			System.out.println("\n"+ch);
			s = sc.next();
//			System.out.println("\n"+s);
			nodeHashTable.put(ch.charAt(0), new node(ch,s));
			x=0;
			if(s.equals("NULL"))
				length = 0;
			else
				length = s.length();
			nodeInd = nodeHashTable.get(ch.charAt(0));
//			System.out.println("Node got : "+mNode.name);
			while(x<Math.pow(2, length)){
				nodeInd.prob[x] = sc.nextFloat();
				x++;
			}
		}
		float answer = 1,divisor = 1;
		fulfilled = new ArrayList<String>();
		x=0;
		while(x < calc.size()){
//			System.out.println("Processing : "+find.get(i));
			if (!fulfilled.contains(calc.get(x))) {
				answer *= calcProbability(calc.get(x), calc);
				fulfilled.add(calc.get(x));
			}/*else*/
//				System.out.println("\nAlready Done : "+find.get(i));
			x++;
		}
		fulfilled.clear();
		if(given!=null){
			x=0;
			while(x < given.size()){
				if (!fulfilled.contains(given.get(x))) {
					divisor *= calcProbability(given.get(x), given);
					fulfilled.add(given.get(x));
				}
				x++;
			}
		}
		answer = answer / divisor;
		System.out.println("\n The Probability of the given arguments = "+answer);
	}

}

class node{
	public String name;
	public String parent;
	public float prob[];

	node(String ch,String p){
		name = ch;
		if(p.equals("NULL"))
			parent = null;
		else
			parent = p;
		prob = new float[(int) Math.pow(2, p.length())];
//		System.out.println("\n\nNode : "+name+"\n Parents : "+parent);
	}
};
