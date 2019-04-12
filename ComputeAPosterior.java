import java.util.*;
import java.io.*;
public class ComputeAPosterior {
	public static void main(String args[]) throws IOException{
		
		PrintWriter printer = new PrintWriter(new FileWriter("result.txt"));
			
				if( args.length > 1 ) 
			    {
			      System.out.println(" Number of arguments should be 1");
			      System.exit(0);
			     }
				
		
			String arguments;
			if(args.length == 0){
				arguments="";
			}else {
				arguments=args[0].toString().toUpperCase();
			}
			int length= arguments.length();
		
		// code for finding number of cherries and limes
		char heap[]=arguments.toCharArray();
		int counterC=0,counterL=0;
		for(int i=0;i<length;i++){
	
			if (heap[i]=='C') counterC++;
			else if (heap[i]=='L') counterL++;
			
			double alpha=0.00;
			//P(h1|q)
			double h1=0.1,c1=1.0,l1=0.0;
			
			Double H1alpha = Math.pow(c1,counterC)*Math.pow(l1,counterL)*h1;	
			double h2=0.2,c2=0.75,l2=0.25;
			Double H2alpha = Math.pow(c2,counterC)*Math.pow(l2,counterL)*h2;		
			double h3=0.4,c3=0.5,l3=0.5;
			Double H3alpha = Math.pow(c3,counterC)*Math.pow(l3,counterL)*h3;		
			double h4=0.2,c4=0.25,l4=0.75;
			Double H4alpha = Math.pow(c4,counterC)*Math.pow(l4,counterL)*h4;		
			double h5=0.1,c5=0.0,l5=1.0;
			Double H5alpha = Math.pow(c5,counterC)*Math.pow(l5,counterL)*h5;			
			alpha=computeAlpha(H1alpha,H2alpha,H3alpha,H4alpha,H5alpha);
			
			H1alpha= alpha*H1alpha;
			H2alpha= alpha*H2alpha;
			H3alpha= alpha*H3alpha;
			H4alpha= alpha*H4alpha;
			H5alpha= alpha*H5alpha;
			output(H1alpha,H2alpha,H3alpha,H4alpha,H5alpha,c1,c2,c3,c4,c5,l1,l2,l3,l4,l5,arguments,i,printer);
			
		}
	printer.close();
		
	}

	private static void output(Double h1alpha, Double h2alpha, Double h3alpha,
			Double h4alpha, Double h5alpha, double h1, double h2, double h3, double h4, double h5, double l1, double l2, double l3, double l4, double l5, String input,int i, PrintWriter printer) throws IOException {
		
		double cherry=(h1*h1alpha+h2*h2alpha+h3*h3alpha+h4*h4alpha+h5*h5alpha);
		double lime=(l1*h1alpha+l2*h2alpha+l3*h3alpha+l4*h4alpha+l5*h5alpha);
		String r = System.lineSeparator();
		
		//to file
	

		if(i<1) {
			printer.print("\n Observation sequence Q:"+ " "+ input + r +"\n");
			printer.print("\n Length of Q:"+" "+input.length() + "\r\n");
		}
		printer.print("\n********************************"+ r +"\n");
		printer.print("\nAfter observation " + (i+1) + " = " + input.substring(0, i+1) + r +"\n");
		printer.print("\n********************************"+ r );
		printer.print(r + "\nP(h1 | Q) = "+String.format("%.5f",h1alpha)+ r );
		printer.print("\nP(h2 | Q) = "+String.format("%.5f",h2alpha)+ r );
		printer.print("\nP(h3 | Q) = "+String.format("%.5f",h3alpha)+ r );
		printer.print("\nP(h4 | Q) = "+String.format("%.5f",h4alpha)+ r );
		printer.print("\nP(h5 | Q) = "+String.format("%.5f",h5alpha)+ r );
		printer.print("\n*******************************"+ r );
		printer.print("\nProbability that the next candy we pick will be C, given Q: "+String.format("%.5f",cherry)+ r );	
		printer.print("\nProbability that the next candy we pick will be L, given Q: "+String.format("%.5f",lime)+ r );
		
	}

	public static double computeAlpha(Double h1alpha, Double h2alpha,
			Double h3alpha, Double h4alpha, Double h5alpha) {
		// computing alpha
		double alpha= 1/(h1alpha+h2alpha+h3alpha+h4alpha+h5alpha);
		return alpha;
	}
	
}
