//オセロ(Player1 VS Player2)
import java.io.*;

public class Osero {

	public static void main(String[] args) {
		//盤作成
		String[][] ban=new String[8][8];
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				ban[i][j]=" ・・";
			}
		}
		//初期設定	
		ban[3][3]=" ◎";
		ban[4][4]=" ◎";
		ban[3][4]=" 〇";
		ban[4][3]=" 〇";

		System.out.println("Player1：◎　　　Player2：〇");
		//初期表示
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				System.out.print(ban[i][j]);
			}
			System.out.println();
		}
		//オセロスタート
		System.out.println("START");
		for(int q=0;q<60;q++){
			//--------------------------------------------------------------
			//plyer1
			System.out.println("Player1の番です");
			ban=hint(ban,1);
			System.out.println("        １  ２   ３  ４   ５  ６  ７  ８");
			for(int i=0;i<8;i++){
				System.out.print((i+1)+":");
				for(int j=0;j<8;j++){
					System.out.print(ban[i][j]);
				}
				System.out.println();
			}

			if(sensor(ban,1)==1){
				BufferedReader zahyo=new BufferedReader(new InputStreamReader(System.in));
				try{
					int tate;
					int yoko;
					int errcnt=0;

					do{
						if(errcnt>0){
							System.out.println("そこには置けません");
						}

						System.out.println("上から何番目ですか？");
						String yjiku=zahyo.readLine();
						tate=Integer.parseInt(yjiku);
						System.out.println(tate+"が入力されました");

						System.out.println("左から何番目ですか？");
						String xjiku=zahyo.readLine();
						yoko=Integer.parseInt(xjiku);
						System.out.println(yoko+"が入力されました");

						yoko--;
						tate--;
						errcnt++;

					}
					while(check(ban,tate,yoko,1)==0
							);

					ban=set(ban,tate,yoko,1);
					ban=changeT(ban,tate,yoko,1);
					ban=changeY(ban,tate,yoko,1);
					ban=changeN(ban,tate,yoko,1);

				}catch(IOException e){
					System.out.println(e);
				}
			}else{
				System.out.println("置けるところがないのでパスとなります");
			}
			//--------------------------------------------------------------
			//plyer2
			System.out.println("Plater2の番です");
			ban=hint(ban,2);
			System.out.println("        １  ２   ３  ４   ５  ６  ７  ８");
			for(int i=0;i<8;i++){
				System.out.print((i+1)+":");
				for(int j=0;j<8;j++){
					System.out.print(ban[i][j]);
				}
				System.out.println();
			}

			if(sensor(ban,2)==1){
				BufferedReader zahyo=new BufferedReader(new InputStreamReader(System.in));
				try{
					int tate;
					int yoko;
					int errcnt=0;

					do{
						if(errcnt>0){
							System.out.println("そこには置けません");
						}

						System.out.println("上から何番目ですか？");
						String yjiku=zahyo.readLine();
						tate=Integer.parseInt(yjiku);
						System.out.println(tate+"が入力されました");

						System.out.println("左から何番目ですか？");
						String xjiku=zahyo.readLine();
						yoko=Integer.parseInt(xjiku);
						System.out.println(yoko+"が入力されました");

						yoko--;
						tate--;
						errcnt++;

					}
					while(check(ban,tate,yoko,2)==0
							);

					ban=set(ban,tate,yoko,2);
					ban=changeT(ban,tate,yoko,2);
					ban=changeY(ban,tate,yoko,2);
					ban=changeN(ban,tate,yoko,2);

				}catch(IOException e){
					System.out.println(e);
				}
			}else{
				System.out.println("置けるところがないのでパスとなります");
			}
		}
		//------------------------------------------------------------------
		//勝利判定
		int fincnt1=0;
		int fincnt2=0;
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(ban[i][j]==" ◎"){
					fincnt1++;
				}else if(ban[i][j]==" 〇"){
					fincnt2++;
				}
			}
		}
		System.out.println("FINISH");
		if(fincnt1>fincnt2){
			System.out.println(fincnt1+"対"+fincnt2+"で、Player1の勝ち");
		}else if(fincnt1<fincnt2){
			System.out.println(fincnt1+"対"+fincnt2+"で、Player2の勝ち");
		}else{
			System.out.println(fincnt1+"対"+fincnt2+"で、引き分け");
		}

	}
	//----------------------------------------------------------------------
	//＜メソッド＞置けるところがあるのか  1:OK 0:OUT
	public static int sensor (String[][] ban,int p){
		String[][] ban2=ban;
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(ban2[i][j]==" ・・"||ban[i][j]==" ◆"){
					if(check(ban2,i,j,p)==1){
						return 1;
					}		
				}
			}
		}
		return 0;
	}
	//----------------------------------------------------------------------
	//＜メソッド＞選択されたところに置けるのか 1:OK 0:OUT
	public static int check (String[][] ban,int i,int j,int p){
		String[][] ban2=ban;
		if(i>=0&&i<8&&j>=0&&j<8){
			if(ban[i][j]==" ・・"||ban[i][j]==" ◆"){
				if(checkR(ban2,i,j,p)==1){
					return 1;
				}else{
					return 0;
				}
			}else{
				return 0;
			}
		}else{
			return 0;
		}
	}
	//----------------------------------------------------------------------
	//＜メソッド＞選択されたところに置くとひっくり返るのか？
	public static int checkR (String[][] ban,int i,int j,int p){
		String[][] ban2=ban;
		int cnt=0;
		cnt+=checkT(ban2,i,j,p);
		cnt+=checkY(ban2,i,j,p);
		cnt+=checkN(ban2,i,j,p);
		if(cnt>0){
			return 1;
		}
		return 0;
	}
	//----------------------------------------------------------------------
	//＜メソッド＞選択されたところに置くと縦はひっくり返るのか？
	public static int checkT (String[][] ban,int i,int j,int p){
		String[][] ban2=ban;
		int cnt=0;
		int x=-1;
		if(p==1){
			//上
			for(int u=i-1;u>=0;u--){
				if(ban2[u][j]==" ◎"){
					x=u;
					break;
				}else if(ban2[u][j]==" ・・"||ban2[u][j]==" ◆"){
					x=-1;
					break;
				}
			}
			if(x!=-1){
				for(int u=i-1;u>x;u--){
					cnt++;
				}
			}

			//下
			for(int d=i+1;d<8;d++){
				if(ban2[d][j]==" ◎"){
					x=d;
					break;
				}else if(ban2[d][j]==" ・・"||ban2[d][j]==" ◆"){
					x=-1;
					break;
				}
			}
			if(x!=-1){
				for(int d=i+1;d<x;d++){
					cnt++;
				}
			}

			//
		}else if(p==2){
			for(int u=i-1;u>=0;u--){
				if(ban2[u][j]==" 〇"){
					x=u;
					break;
				}else if(ban2[u][j]==" ・・"||ban2[u][j]==" ◆"){
					x=-1;
					break;
				}
			}
			if(x!=-1){
				for(int u=i-1;u>x;u--){
					cnt++;
				}
			}

			//
			for(int d=i+1;d<8;d++){
				if(ban2[d][j]==" 〇"){
					x=d;
					break;
				}else if(ban2[d][j]==" ・・"||ban2[d][j]==" ◆"){
					x=-1;
					break;
				}
			}
			if(x!=-1){
				for(int d=i+1;d<x;d++){
					cnt++;
				}
			}
		}

		if(cnt>0){
			return cnt;
		}
		return 0;
	}
	//----------------------------------------------------------------------
	//＜メソッド＞選択されたところに置くと横はひっくり返るのか？
	public static int checkY (String[][] ban,int i,int j,int p){
		String[][] ban2=ban;
		int cnt=0;
		int y=-1;
		if(p==1){
			//左
			for(int l=j-1;l>=0;l--){
				if(ban2[i][l]==" ◎"){	
					y=l;
					break;
				}else if(ban2[i][l]==" ・・"||ban2[i][l]==" ◆"){
					y=-1;
					break;
				}
			}
			if(y!=-1){
				for(int l=j-1;l>y;l--){
					cnt++;
				}
			}

			//右
			for(int r=j+1;r<8;r++){
				if(ban2[i][r]==" ◎"){
					y=r;
					break;
				}else if(ban2[i][r]==" ・・"||ban2[i][r]==" ◆"){
					y=-1;
					break;
				}
			}
			if(y!=-1){
				for(int r=j+1;r<y;r++){
					cnt++;
				}
			}

			//
		}else if(p==2){
			for(int l=j-1;l>=0;l--){
				if(ban2[i][l]==" 〇"){
					y=l;
					break;
				}else if(ban2[i][l]==" ・・"||ban2[i][l]==" ◆"){
					y=-1;
					break;
				}
			}
			if(y!=-1){
				for(int l=j-1;l>y;l--){
					cnt++;
				}
			}

			//
			for(int r=j+1;r<8;r++){
				if(ban2[i][r]==" 〇"){	
					y=r;
					break;
				}else if(ban2[i][r]==" ・・"||ban2[i][r]==" ◆"){
					y=-1;
					break;
				}
			}
			if(y!=-1){
				for(int r=j+1;r<y;r++){
					cnt++;
				}
			}
		}
		if(cnt>0){
			return cnt;
		}
		return 0;
	}
	//----------------------------------------------------------------------
	//＜メソッド＞選択されたところに置くとななめはひっくり返るのか？
	public static int checkN (String[][] ban,int i,int j,int p){
		String[][] ban2=ban;
		int cnt=0;
		int z=-1;
		if(p==1){
			//左上
			int t=i;
			int s=j;
			for(int ul=1;ul<=7;ul++){
				t--;
				s--;
				if(t<0||s<0){
					break;
				}
				if(ban2[t][s]==" ◎"){
					z=ul;
					break;
				}else if(ban2[t][s]==" ・・"||ban2[t][s]==" ◆"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				for(int ul=1;ul<z;ul++){
					t--;
					s--;
					cnt++;
				}
			}

			//右上
			z=-1;
			t=i;
			s=j;
			for(int ur=1;ur<=7;ur++){
				t--;
				s++;
				if(t<0||s>=8){
					break;
				}
				if(ban2[t][s]==" ◎"){
					z=ur;
					break;
				}else if(ban2[t][s]==" ・・"||ban2[t][s]==" ◆"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				for(int ur=1;ur<z;ur++){
					t--;
					s++;
					cnt++;
				}
			}

			//左下
			z=-1;
			t=i;
			s=j;
			for(int dl=1;dl<=7;dl++){
				t++;
				s--;
				if(t>=8||s<0){
					break;
				}
				if(ban2[t][s]==" ◎"){
					z=dl;
					break;
				}else if(ban2[t][s]==" ・・"||ban2[t][s]==" ◆"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				for(int dl=1;dl<z;dl++){
					t++;
					s--;
					cnt++;
				}
			}

			//右下
			z=-1;
			t=i;
			s=j;
			for(int dr=1;dr<=7;dr++){
				t++;
				s++;
				if(t>=8||s>=8){
					break;
				}
				if(ban2[t][s]==" ◎"){
					z=dr;
					break;
				}else if(ban2[t][s]==" ・・"||ban2[t][s]==" ◆"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				for(int dr=1;dr<z;dr++){
					t++;
					s++;
					cnt++;
				}
			}

			//
		}else if(p==2){
			z=-1;
			int t=i;
			int s=j;
			for(int ul=1;ul<=7;ul++){
				t--;
				s--;
				if(t<0||s<0){
					break;
				}
				if(ban2[t][s]==" 〇"){	
					z=ul;
					break;
				}else if(ban2[t][s]==" ・・"||ban2[t][s]==" ◆"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				for(int ul=1;ul<z;ul++){
					t--;
					s--;
					cnt++;
				}
			}

			//
			z=-1;
			t=i;
			s=j;
			for(int ur=1;ur<=7;ur++){
				t--;
				s++;
				if(t<0||s>=8){
					break;
				}
				if(ban2[t][s]==" 〇"){
					z=ur;
					break;
				}else if(ban2[t][s]==" ・・"||ban2[t][s]==" ◆"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				for(int ur=1;ur<z;ur++){
					t--;
					s++;
					cnt++;
				}
			}

			//
			z=-1;
			t=i;
			s=j;
			for(int dl=1;dl<=7;dl++){
				t++;
				s--;
				if(t>=8||s<0){
					break;
				}
				if(ban2[t][s]==" 〇"){
					z=dl;
					break;
				}else if(ban2[t][s]==" ・・"||ban2[t][s]==" ◆"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				for(int dl=1;dl<z;dl++){
					t++;
					s--;
					cnt++;
				}
			}

			//
			z=-1;
			t=i;
			s=j;
			for(int dr=1;dr<=7;dr++){
				t++;
				s++;
				if(t>=8||s>=8){
					break;
				}
				if(ban2[t][s]==" 〇"){		
					z=dr;
					break;
				}else if(ban2[t][s]==" ・・"||ban2[t][s]==" ◆"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				for(int dr=1;dr<z;dr++){
					t++;
					s++;
					cnt++;
				}
			}
		}
		if(cnt>0){
			return cnt;
		}
		return 0;
	}
	//----------------------------------------------------------------------
	//＜メソッド＞選択されたところに設置　 1:◎　2:〇
	public static String[][] set(String[][] ban,int i,int j,int p){
		if(p==1){
			ban[i][j]=" ◎";
		}else if(p==2){
			ban[i][j]=" 〇";
		}
		return ban;
	}
	//----------------------------------------------------------------------
	//＜メソッド＞縦にひっくり返す　 1:◎　2:〇
	public static String[][] changeT(String[][] ban,int i,int j,int p){
		int x=-1;
		if(p==1){
			//上
			for(int u=i-1;u>=0;u--){
				if(ban[u][j]==" ◎"){
					x=u;
					break;
				}else if(ban[u][j]==" ・・"||ban[u][j]==" ◆"){
					x=-1;
					break;
				}
			}
			if(x!=-1){
				for(int u=i-1;u>x;u--){
					ban[u][j]=" ◎";
				}
			}

			//下
			for(int d=i+1;d<8;d++){
				if(ban[d][j]==" ◎"){
					x=d;
					break;
				}else if(ban[d][j]==" ・・"||ban[d][j]==" ◆"){
					x=-1;
					break;
				}
			}
			if(x!=-1){
				for(int d=i+1;d<x;d++){
					ban[d][j]=" ◎";
				}
			}

			//
		}else if(p==2){
			for(int u=i-1;u>=0;u--){
				if(ban[u][j]==" 〇"){
					x=u;
					break;
				}else if(ban[u][j]==" ・・"||ban[u][j]==" ◆"){
					x=-1;
					break;
				}
			}
			if(x!=-1){
				for(int u=i-1;u>x;u--){
					ban[u][j]=" 〇";
				}
			}

			//
			for(int d=i+1;d<8;d++){
				if(ban[d][j]==" 〇"){
					x=d;
					break;
				}else if(ban[d][j]==" ・・"||ban[d][j]==" ◆"){
					x=-1;
					break;
				}
			}
			if(x!=-1){
				for(int d=i+1;d<x;d++){
					ban[d][j]=" 〇";
				}
			}
		}
		return ban;
	}
	//----------------------------------------------------------------------
	//＜メソッド＞横にひっくり返す　 1:◎　2:〇
	public static String[][] changeY(String[][] ban,int i,int j,int p){
		int y=-1;
		if(p==1){
			//左
			for(int l=j-1;l>=0;l--){
				if(ban[i][l]==" ◎"){	
					y=l;
					break;
				}else if(ban[i][l]==" ・・"||ban[i][l]==" ◆"){
					y=-1;
					break;
				}
			}
			if(y!=-1){
				for(int l=j-1;l>y;l--){
					ban[i][l]=" ◎";
				}
			}

			//右
			for(int r=j+1;r<8;r++){
				if(ban[i][r]==" ◎"){
					y=r;
					break;
				}else if(ban[i][r]==" ・・"||ban[i][r]==" ◆"){
					y=-1;
					break;
				}
			}
			if(y!=-1){
				for(int r=j+1;r<y;r++){
					ban[i][r]=" ◎";
				}
			}

			//
		}else if(p==2){
			for(int l=j-1;l>=0;l--){
				if(ban[i][l]==" 〇"){
					y=l;
					break;
				}else if(ban[i][l]==" ・・"||ban[i][l]==" ◆"){
					y=-1;
					break;
				}
			}
			if(y!=-1){
				for(int l=j-1;l>y;l--){
					ban[i][l]=" 〇";
				}
			}

			//
			for(int r=j+1;r<8;r++){
				if(ban[i][r]==" 〇"){	
					y=r;
					break;
				}else if(ban[i][r]==" ・・"||ban[i][r]==" ◆"){
					y=-1;
					break;
				}
			}
			if(y!=-1){
				for(int r=j+1;r<y;r++){
					ban[i][r]=" 〇";
				}
			}
		}
		return ban;
	}
	//----------------------------------------------------------------------
	//＜メソッド＞ななめにひっくり返す　 1:◎　2:〇
	public static String[][] changeN(String[][] ban,int i,int j,int p){
		int z=-1;
		if(p==1){
			//左上
			int t=i;
			int s=j;
			for(int ul=1;ul<=7;ul++){
				t--;
				s--;
				if(t<0||s<0){
					break;
				}
				if(ban[t][s]==" ◎"){
					z=ul;
					break;
				}else if(ban[t][s]==" ・・"||ban[t][s]==" ◆"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				for(int ul=1;ul<z;ul++){
					t--;
					s--;
					ban[t][s]=" ◎";
				}
			}

			//右上
			z=-1;
			t=i;
			s=j;
			for(int ur=1;ur<=7;ur++){
				t--;
				s++;
				if(t<0||s>=8){
					break;
				}
				if(ban[t][s]==" ◎"){
					z=ur;
					break;
				}else if(ban[t][s]==" ・・"||ban[t][s]==" ◆"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				for(int ur=1;ur<z;ur++){
					t--;
					s++;
					ban[t][s]=" ◎";
				}
			}

			//左下
			z=-1;
			t=i;
			s=j;
			for(int dl=1;dl<=7;dl++){
				t++;
				s--;
				if(t>=8||s<0){
					break;
				}
				if(ban[t][s]==" ◎"){
					z=dl;
					break;
				}else if(ban[t][s]==" ・・"||ban[t][s]==" ◆"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				for(int dl=1;dl<z;dl++){
					t++;
					s--;
					ban[t][s]=" ◎";
				}
			}

			//右下
			z=-1;
			t=i;
			s=j;
			for(int dr=1;dr<=7;dr++){
				t++;
				s++;
				if(t>=8||s>=8){
					break;
				}
				if(ban[t][s]==" ◎"){
					z=dr;
					break;
				}else if(ban[t][s]==" ・・"||ban[t][s]==" ◆"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				for(int dr=1;dr<z;dr++){
					t++;
					s++;
					ban[t][s]=" ◎";
				}
			}

			//
		}else if(p==2){
			z=-1;
			int t=i;
			int s=j;
			for(int ul=1;ul<=7;ul++){
				t--;
				s--;
				if(t<0||s<0){
					break;
				}
				if(ban[t][s]==" 〇"){	
					z=ul;
					break;
				}else if(ban[t][s]==" ・・"||ban[t][s]==" ◆"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				for(int ul=1;ul<z;ul++){
					t--;
					s--;
					ban[t][s]=" 〇";
				}
			}

			//
			z=-1;
			t=i;
			s=j;
			for(int ur=1;ur<=7;ur++){
				t--;
				s++;
				if(t<0||s>=8){
					break;
				}
				if(ban[t][s]==" 〇"){
					z=ur;
					break;
				}else if(ban[t][s]==" ・・"||ban[t][s]==" ◆"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				for(int ur=1;ur<z;ur++){
					t--;
					s++;
					ban[t][s]=" 〇";
				}
			}

			//
			z=-1;
			t=i;
			s=j;
			for(int dl=1;dl<=7;dl++){
				t++;
				s--;
				if(t>=8||s<0){
					break;
				}
				if(ban[t][s]==" 〇"){
					z=dl;
					break;
				}else if(ban[t][s]==" ・・"||ban[t][s]==" ◆"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				for(int dl=1;dl<z;dl++){
					t++;
					s--;
					ban[t][s]=" 〇";
				}
			}

			//
			z=-1;
			t=i;
			s=j;
			for(int dr=1;dr<=7;dr++){
				t++;
				s++;
				if(t>=8||s>=8){
					break;
				}
				if(ban[t][s]==" 〇"){		
					z=dr;
					break;
				}else if(ban[t][s]==" ・・"||ban[t][s]==" ◆"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				for(int dr=1;dr<z;dr++){
					t++;
					s++;
					ban[t][s]=" 〇";
				}
			}
		}
		return ban;
	}
	//-----------------------------------------------------------
	//＜メソッド＞置けるところを教える　 1:◎　2:〇
	public static String[][] hint(String[][] ban,int p){

		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(ban[i][j]==" ◆"){
					ban[i][j]=" ・・";
				}
				if(ban[i][j]!=" ◎"&&ban[i][j]!=" 〇"){
					if(check(ban,i,j,p)==1){
						ban[i][j]=" ◆";
					}
				}
			}
		}
		return ban;
	}
}





















