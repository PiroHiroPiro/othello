import java.io.*;

public class othelo2 {

	public static void main(String[] args) {
		//�Ս쐬
		String[][] ban=new String[8][8];
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				ban[i][j]=" �E�E";
			}
		}
		//�����ݒ�	
		ban[3][3]=" ��";
		ban[4][4]=" ��";
		ban[3][4]=" �Z";
		ban[4][3]=" �Z";

		System.out.println("Player�F���@�@�@Computer�F�Z");
		//�����\��
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				System.out.print(ban[i][j]);
			}
			System.out.println();
		}
		//�I�Z���X�^�[�g
		System.out.println("START");
		for(int q=0;q<60;q++){
			//--------------------------------------------------------------
			//plyer1
			System.out.println("Player�̔Ԃł�");
			ban=hint(ban,1);
			System.out.println("        �P  �Q   �R  �S   �T  �U  �V  �W");
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
							System.out.println("�����ɂ͒u���܂���");
						}

						System.out.println("�ォ�牽�Ԗڂł����H");
						String yjiku=zahyo.readLine();
						tate=Integer.parseInt(yjiku);
						System.out.println(tate+"�����͂���܂���");

						System.out.println("�����牽�Ԗڂł����H");
						String xjiku=zahyo.readLine();
						yoko=Integer.parseInt(xjiku);
						System.out.println(yoko+"�����͂���܂���");

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
				System.out.println("�u����Ƃ��낪�Ȃ��̂Ńp�X�ƂȂ�܂�");
			}
			//--------------------------------------------------------------
			//Computer
			System.out.println("Computer�̔Ԃł�");
			ban=hint(ban,2);
			System.out.println("        �P  �Q   �R  �S   �T  �U  �V  �W");
			for(int i=0;i<8;i++){
				System.out.print((i+1)+":");
				for(int j=0;j<8;j++){
					System.out.print(ban[i][j]);
				}
				System.out.println();
			}

			//�܂��A�u����Ƃ�����s�b�N�A�b�v
			if(sensor(ban,2)==1){
				int tate=0;
				int yoko=0;
				int[] reC=new int[2];
				int[] reS=new int[2];
				
				reC=choice(ban,2);
				tate=reC[0];
				yoko=reC[1];

				reS=kimura(ban,2);
				if(ban[reS[0]][reS[1]]==" ��"){
					tate=reS[0];
					yoko=reS[1];
				}
				
				tate++;
				yoko++;

				System.out.println("�ォ�牽�Ԗڂł����H");
				System.out.println(tate+"�����͂���܂���");
				System.out.println("�����牽�Ԗڂł����H");
				System.out.println(yoko+"�����͂���܂���");

				tate--;
				yoko--;

				ban=set(ban,tate,yoko,2);
				ban=changeT(ban,tate,yoko,2);
				ban=changeY(ban,tate,yoko,2);
				ban=changeN(ban,tate,yoko,2);


			}else{
				System.out.println("�u����Ƃ��낪�Ȃ��̂Ńp�X�ƂȂ�܂�");
			}
		}

		//------------------------------------------------------------------
		//��������
		int fincnt1=0;
		int fincnt2=0;
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(ban[i][j]==" ��"){
					fincnt1++;
				}else if(ban[i][j]==" �Z"){
					fincnt2++;
				}
			}
		}
		System.out.println("FINISH");
		if(fincnt1>fincnt2){
			System.out.println(fincnt1+"��"+fincnt2+"�ŁAPlayer�̏���");
		}else if(fincnt1<fincnt2){
			System.out.println(fincnt1+"��"+fincnt2+"�ŁAComputer�̏���");
		}else{
			System.out.println(fincnt1+"��"+fincnt2+"�ŁA��������");
		}

	}
	//----------------------------------------------------------------------
	//�����\�b�h���u����Ƃ��낪����̂�  1:OK 0:OUT
	public static int sensor (String[][] ban,int p){
		String[][] ban2=ban;
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(ban2[i][j]==" �E�E"||ban2[i][j]==" ��"){
					if(check(ban2,i,j,p)==1){
						return 1;
					}		
				}
			}
		}
		return 0;
	}
	//----------------------------------------------------------------------
	//�����\�b�h���I�����ꂽ�Ƃ���ɒu����̂� 1:OK 0:OUT
	public static int check (String[][] ban,int i,int j,int p){
		String[][] ban2=ban;
		if(i>=0&&i<8&&j>=0&&j<8){
			if(ban2[i][j]==" �E�E"||ban2[i][j]==" ��"){
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
	//�����\�b�h���I�����ꂽ�Ƃ���ɒu���ƂЂ�����Ԃ�̂��H
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
	//�����\�b�h���I�����ꂽ�Ƃ���ɒu���Əc�͂Ђ�����Ԃ�̂��H
	public static int checkT (String[][] ban,int i,int j,int p){
		String[][] ban2=ban;
		int cnt1=0;
		int x=-1;
		if(p==1){
			//��
			for(int u=i-1;u>=0;u--){
				if(ban2[u][j]==" ��"){
					x=u;
					break;
				}else if(ban2[u][j]==" �E�E"||ban2[u][j]==" ��"){
					x=-1;
					break;
				}
			}
			if(x!=-1){
				cnt1--;
				for(int u=i-1;u>=x;u--){
					cnt1++;
				}
			}

			//��
			for(int d=i+1;d<8;d++){
				if(ban2[d][j]==" ��"){
					x=d;
					break;
				}else if(ban2[d][j]==" �E�E"||ban2[d][j]==" ��"){
					x=-1;
					break;
				}
			}
			if(x!=-1){
				cnt1--;
				for(int d=i+1;d<=x;d++){
					cnt1++;
				}
			}

			//
		}else if(p==2){
			for(int u=i-1;u>=0;u--){
				if(ban2[u][j]==" �Z"){
					x=u;
					break;
				}else if(ban2[u][j]==" �E�E"||ban2[u][j]==" ��"){
					x=-1;
					break;
				}
			}
			if(x!=-1){
				cnt1--;
				for(int u=i-1;u>=x;u--){
					cnt1++;
				}
			}

			//
			for(int d=i+1;d<8;d++){
				if(ban2[d][j]==" �Z"){
					x=d;
					break;
				}else if(ban2[d][j]==" �E�E"||ban2[d][j]==" ��"){
					x=-1;
					break;
				}
			}
			if(x!=-1){
				cnt1--;
				for(int d=i+1;d<=x;d++){
					cnt1++;
				}
			}
		}

		if(cnt1>0){
			return cnt1;
		}
		return 0;
	}
	//----------------------------------------------------------------------
	//�����\�b�h���I�����ꂽ�Ƃ���ɒu���Ɖ��͂Ђ�����Ԃ�̂��H
	public static int checkY (String[][] ban,int i,int j,int p){
		String[][] ban2=ban;
		int cnt2=0;
		int y=-1;
		if(p==1){
			//��
			for(int l=j-1;l>=0;l--){
				if(ban2[i][l]==" ��"){	
					y=l;
					break;
				}else if(ban2[i][l]==" �E�E"||ban2[i][l]==" ��"){
					y=-1;
					break;
				}
			}
			if(y!=-1){
				cnt2--;
				for(int l=j-1;l>=y;l--){
					cnt2++;
				}
			}

			//�E
			for(int r=j+1;r<8;r++){
				if(ban2[i][r]==" ��"){
					y=r;
					break;
				}else if(ban2[i][r]==" �E�E"||ban2[i][r]==" ��"){
					y=-1;
					break;
				}
			}
			if(y!=-1){
				cnt2--;
				for(int r=j+1;r<=y;r++){
					cnt2++;
				}
			}

			//
		}else if(p==2){
			for(int l=j-1;l>=0;l--){
				if(ban2[i][l]==" �Z"){
					y=l;
					break;
				}else if(ban2[i][l]==" �E�E"||ban2[i][l]==" ��"){
					y=-1;
					break;
				}
			}
			if(y!=-1){
				cnt2--;
				for(int l=j-1;l>=y;l--){
					cnt2++;
				}
			}

			//
			for(int r=j+1;r<8;r++){
				if(ban2[i][r]==" �Z"){	
					y=r;
					break;
				}else if(ban2[i][r]==" �E�E"||ban2[i][r]==" ��"){
					y=-1;
					break;
				}
			}
			if(y!=-1){
				cnt2--;
				for(int r=j+1;r<=y;r++){
					cnt2++;
				}
			}
		}
		if(cnt2>0){
			return cnt2;
		}
		return 0;
	}
	//----------------------------------------------------------------------
	//�����\�b�h���I�����ꂽ�Ƃ���ɒu���ƂȂȂ߂͂Ђ�����Ԃ�̂��H
	public static int checkN (String[][] ban,int i,int j,int p){
		String[][] ban2=ban;
		int cnt3=0;
		int z=-1;
		if(p==1){
			//����
			int t=i;
			int s=j;
			for(int ul=1;ul<=7;ul++){
				t--;
				s--;
				if(t<0||s<0){
					break;
				}
				if(ban2[t][s]==" ��"){
					z=ul;
					break;
				}else if(ban2[t][s]==" �E�E"||ban2[t][s]==" ��"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				cnt3--;
				for(int ul=1;ul<=z;ul++){
					t--;
					s--;
					cnt3++;
				}
			}

			//�E��
			z=-1;
			t=i;
			s=j;
			for(int ur=1;ur<=7;ur++){
				t--;
				s++;
				if(t<0||s>=8){
					break;
				}
				if(ban2[t][s]==" ��"){
					z=ur;
					break;
				}else if(ban2[t][s]==" �E�E"||ban2[t][s]==" ��"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				cnt3--;
				for(int ur=1;ur<=z;ur++){
					t--;
					s++;
					cnt3++;
				}
			}

			//����
			z=-1;
			t=i;
			s=j;
			for(int dl=1;dl<=7;dl++){
				t++;
				s--;
				if(t>=8||s<0){
					break;
				}
				if(ban2[t][s]==" ��"){
					z=dl;
					break;
				}else if(ban2[t][s]==" �E�E"||ban2[t][s]==" ��"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				cnt3--;
				for(int dl=1;dl<=z;dl++){
					t++;
					s--;
					cnt3++;
				}
			}

			//�E��
			z=-1;
			t=i;
			s=j;
			for(int dr=1;dr<=7;dr++){
				t++;
				s++;
				if(t>=8||s>=8){
					break;
				}
				if(ban2[t][s]==" ��"){
					z=dr;
					break;
				}else if(ban2[t][s]==" �E�E"||ban2[t][s]==" ��"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				cnt3--;
				for(int dr=1;dr<=z;dr++){
					t++;
					s++;
					cnt3++;
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
				if(ban2[t][s]==" �Z"){	
					z=ul;
					break;
				}else if(ban2[t][s]==" �E�E"||ban2[t][s]==" ��"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				cnt3--;
				for(int ul=1;ul<=z;ul++){
					t--;
					s--;
					cnt3++;
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
				if(ban2[t][s]==" �Z"){
					z=ur;
					break;
				}else if(ban2[t][s]==" �E�E"||ban2[t][s]==" ��"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				cnt3--;
				for(int ur=1;ur<=z;ur++){
					t--;
					s++;
					cnt3++;
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
				if(ban2[t][s]==" �Z"){
					z=dl;
					break;
				}else if(ban2[t][s]==" �E�E"||ban2[t][s]==" ��"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				cnt3--;
				for(int dl=1;dl<=z;dl++){
					t++;
					s--;
					cnt3++;
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
				if(ban2[t][s]==" �Z"){		
					z=dr;
					break;
				}else if(ban2[t][s]==" �E�E"||ban2[t][s]==" ��"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				cnt3--;
				for(int dr=1;dr<=z;dr++){
					t++;
					s++;
					cnt3++;
				}
			}
		}
		if(cnt3>0){
			return cnt3;
		}
		return 0;
	}
	//----------------------------------------------------------------------
	//�����\�b�h���I�����ꂽ�Ƃ���ɐݒu�@ 1:���@2:�Z
	public static String[][] set(String[][] ban,int i,int j,int p){
		if(p==1){
			ban[i][j]=" ��";
		}else if(p==2){
			ban[i][j]=" �Z";
		}
		return ban;
	}
	//----------------------------------------------------------------------
	//�����\�b�h���c�ɂЂ�����Ԃ��@ 1:���@2:�Z
	public static String[][] changeT(String[][] ban,int i,int j,int p){
		int x=-1;
		if(p==1){
			//��
			for(int u=i-1;u>=0;u--){
				if(ban[u][j]==" ��"){
					x=u;
					break;
				}else if(ban[u][j]==" �E�E"||ban[u][j]==" ��"){
					x=-1;
					break;
				}
			}
			if(x!=-1){
				for(int u=i-1;u>=x;u--){
					ban[u][j]=" ��";
				}
			}

			//��
			for(int d=i+1;d<8;d++){
				if(ban[d][j]==" ��"){
					x=d;
					break;
				}else if(ban[d][j]==" �E�E"||ban[d][j]==" ��"){
					x=-1;
					break;
				}
			}
			if(x!=-1){
				for(int d=i+1;d<=x;d++){
					ban[d][j]=" ��";
				}
			}

			//
		}else if(p==2){
			for(int u=i-1;u>=0;u--){
				if(ban[u][j]==" �Z"){
					x=u;
					break;
				}else if(ban[u][j]==" �E�E"||ban[u][j]==" ��"){
					x=-1;
					break;
				}
			}
			if(x!=-1){
				for(int u=i-1;u>=x;u--){
					ban[u][j]=" �Z";
				}
			}

			//
			for(int d=i+1;d<8;d++){
				if(ban[d][j]==" �Z"){
					x=d;
					break;
				}else if(ban[d][j]==" �E�E"||ban[d][j]==" ��"){
					x=-1;
					break;
				}
			}
			if(x!=-1){
				for(int d=i+1;d<=x;d++){
					ban[d][j]=" �Z";
				}
			}
		}
		return ban;
	}
	//----------------------------------------------------------------------
	//�����\�b�h�����ɂЂ�����Ԃ��@ 1:���@2:�Z
	public static String[][] changeY(String[][] ban,int i,int j,int p){
		int y=-1;
		if(p==1){
			//��
			for(int l=j-1;l>=0;l--){
				if(ban[i][l]==" ��"){	
					y=l;
					break;
				}else if(ban[i][l]==" �E�E"||ban[i][l]==" ��"){
					y=-1;
					break;
				}
			}
			if(y!=-1){
				for(int l=j-1;l>=y;l--){
					ban[i][l]=" ��";
				}
			}

			//�E
			for(int r=j+1;r<8;r++){
				if(ban[i][r]==" ��"){
					y=r;
					break;
				}else if(ban[i][r]==" �E�E"||ban[i][r]==" ��"){
					y=-1;
					break;
				}
			}
			if(y!=-1){
				for(int r=j+1;r<=y;r++){
					ban[i][r]=" ��";
				}
			}

			//
		}else if(p==2){
			for(int l=j-1;l>=0;l--){
				if(ban[i][l]==" �Z"){
					y=l;
					break;
				}else if(ban[i][l]==" �E�E"||ban[i][l]==" ��"){
					y=-1;
					break;
				}
			}
			if(y!=-1){
				for(int l=j-1;l>=y;l--){
					ban[i][l]=" �Z";
				}
			}

			//
			for(int r=j+1;r<8;r++){
				if(ban[i][r]==" �Z"){	
					y=r;
					break;
				}else if(ban[i][r]==" �E�E"||ban[i][r]==" ��"){
					y=-1;
					break;
				}
			}
			if(y!=-1){
				for(int r=j+1;r<=y;r++){
					ban[i][r]=" �Z";
				}
			}
		}
		return ban;
	}
	//----------------------------------------------------------------------
	//�����\�b�h���ȂȂ߂ɂЂ�����Ԃ��@ 1:���@2:�Z
	public static String[][] changeN(String[][] ban,int i,int j,int p){
		int z=-1;
		if(p==1){
			//����
			int t=i;
			int s=j;
			for(int ul=1;ul<=7;ul++){
				t--;
				s--;
				if(t<0||s<0){
					break;
				}
				if(ban[t][s]==" ��"){
					z=ul;
					break;
				}else if(ban[t][s]==" �E�E"||ban[t][s]==" ��"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				for(int ul=1;ul<=z;ul++){
					t--;
					s--;
					ban[t][s]=" ��";
				}
			}

			//�E��
			z=-1;
			t=i;
			s=j;
			for(int ur=1;ur<=7;ur++){
				t--;
				s++;
				if(t<0||s>=8){
					break;
				}
				if(ban[t][s]==" ��"){
					z=ur;
					break;
				}else if(ban[t][s]==" �E�E"||ban[t][s]==" ��"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				for(int ur=1;ur<=z;ur++){
					t--;
					s++;
					ban[t][s]=" ��";
				}
			}

			//����
			z=-1;
			t=i;
			s=j;
			for(int dl=1;dl<=7;dl++){
				t++;
				s--;
				if(t>=8||s<0){
					break;
				}
				if(ban[t][s]==" ��"){
					z=dl;
					break;
				}else if(ban[t][s]==" �E�E"||ban[t][s]==" ��"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				for(int dl=1;dl<=z;dl++){
					t++;
					s--;
					ban[t][s]=" ��";
				}
			}

			//�E��
			z=-1;
			t=i;
			s=j;
			for(int dr=1;dr<=7;dr++){
				t++;
				s++;
				if(t>=8||s>=8){
					break;
				}
				if(ban[t][s]==" ��"){
					z=dr;
					break;
				}else if(ban[t][s]==" �E�E"||ban[t][s]==" ��"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				for(int dr=1;dr<=z;dr++){
					t++;
					s++;
					ban[t][s]=" ��";
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
				if(ban[t][s]==" �Z"){	
					z=ul;
					break;
				}else if(ban[t][s]==" �E�E"||ban[t][s]==" ��"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				for(int ul=1;ul<=z;ul++){
					t--;
					s--;
					ban[t][s]=" �Z";
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
				if(ban[t][s]==" �Z"){
					z=ur;
					break;
				}else if(ban[t][s]==" �E�E"||ban[t][s]==" ��"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				for(int ur=1;ur<=z;ur++){
					t--;
					s++;
					ban[t][s]=" �Z";
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
				if(ban[t][s]==" �Z"){
					z=dl;
					break;
				}else if(ban[t][s]==" �E�E"||ban[t][s]==" ��"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				for(int dl=1;dl<=z;dl++){
					t++;
					s--;
					ban[t][s]=" �Z";
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
				if(ban[t][s]==" �Z"){		
					z=dr;
					break;
				}else if(ban[t][s]==" �E�E"||ban[t][s]==" ��"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				for(int dr=1;dr<=z;dr++){
					t++;
					s++;
					ban[t][s]=" �Z";
				}
			}
		}
		return ban;
	}
	//----------------------------------------------------------------------
	//�����\�b�h���I�����ꂽ�Ƃ���ɒu����̂� 1:OK 0:OUT
	public static int checkC (String[][] ban,int i,int j,int p){
		String[][] ban2=ban;
		if(ban[i][j]==" �E�E"||ban[i][j]==" ��"){
			return checkRC(ban2,i,j,p);
		}else{
			return 0;
		}
	}
	//----------------------------------------------------------------------
	//�����\�b�h���I�����ꂽ�Ƃ���ɒu���ƂЂ�����Ԃ�̂��H
	public static int checkRC (String[][] ban,int i,int j,int p){
		String[][] ban2=ban;
		int cnt=0;
		cnt+=checkTC(ban2,i,j,p);
		cnt+=checkYC(ban2,i,j,p);
		cnt+=checkNC(ban2,i,j,p);
		return cnt;
	}
	//----------------------------------------------------------------------
	//�����\�b�h���I�����ꂽ�Ƃ���ɒu���Əc�͂Ђ�����Ԃ�̂��H
	public static int checkTC (String[][] ban,int i,int j,int p){
		String[][] ban2=ban;
		int cnt5=0;
		int x=-1;
		if(p==1){
			//��
			for(int u=i-1;u>=0;u--){
				if(ban2[u][j]==" ��"){
					x=u;
					break;
				}else if(ban2[u][j]==" �E�E"||ban2[u][j]==" ��"){
					x=-1;
					break;
				}
			}
			if(x!=-1){
				cnt5--;
				for(int u=i-1;u>=x;u--){
					cnt5++;
				}
			}

			//��
			for(int d=i+1;d<8;d++){
				if(ban2[d][j]==" ��"){
					x=d;
					break;
				}else if(ban2[d][j]==" �E�E"||ban2[d][j]==" ��"){
					x=-1;
					break;
				}
			}
			if(x!=-1){
				cnt5--;
				for(int d=i+1;d<=x;d++){
					cnt5++;
				}
			}

			//
		}else if(p==2){
			for(int u=i-1;u>=0;u--){
				if(ban2[u][j]==" �Z"){
					x=u;
					break;
				}else if(ban2[u][j]==" �E�E"||ban2[u][j]==" ��"){
					x=-1;
					break;
				}
			}
			if(x!=-1){
				cnt5--;
				for(int u=i-1;u>=x;u--){
					cnt5++;
				}
			}

			//
			for(int d=i+1;d<8;d++){
				if(ban2[d][j]==" �Z"){
					x=d;
					break;
				}else if(ban2[d][j]==" �E�E"||ban2[d][j]==" ��"){
					x=-1;
					break;
				}
			}
			if(x!=-1){
				cnt5--;
				for(int d=i+1;d<=x;d++){
					cnt5++;
				}
			}
		}

		return cnt5;
	}
	//----------------------------------------------------------------------
	//�����\�b�h���I�����ꂽ�Ƃ���ɒu���Ɖ��͂Ђ�����Ԃ�̂��H
	public static int checkYC (String[][] ban,int i,int j,int p){
		String[][] ban2=ban;
		int cnt6=0;
		int y=-1;
		if(p==1){
			//��
			for(int l=j-1;l>=0;l--){
				if(ban2[i][l]==" ��"){	
					y=l;
					break;
				}else if(ban2[i][l]==" �E�E"||ban2[i][l]==" ��"){
					y=-1;
					break;
				}
			}
			if(y!=-1){
				cnt6--;
				for(int l=j-1;l>=y;l--){
					cnt6++;
				}
			}

			//�E
			for(int r=j+1;r<8;r++){
				if(ban2[i][r]==" ��"){
					y=r;
					break;
				}else if(ban2[i][r]==" �E�E"||ban2[i][r]==" ��"){
					y=-1;
					break;
				}
			}
			if(y!=-1){
				cnt6--;
				for(int r=j+1;r<=y;r++){
					cnt6++;
				}
			}

			//
		}else if(p==2){
			for(int l=j-1;l>=0;l--){
				if(ban2[i][l]==" �Z"){
					y=l;
					break;
				}else if(ban2[i][l]==" �E�E"||ban2[i][l]==" ��"){
					y=-1;
					break;
				}
			}
			if(y!=-1){
				cnt6--;
				for(int l=j-1;l>=y;l--){
					cnt6++;
				}
			}

			//
			for(int r=j+1;r<8;r++){
				if(ban2[i][r]==" �Z"){	
					y=r;
					break;
				}else if(ban2[i][r]==" �E�E"||ban2[i][r]==" ��"){
					y=-1;
					break;
				}
			}
			if(y!=-1){
				cnt6--;
				for(int r=j+1;r<=y;r++){
					cnt6++;
				}
			}
		}

		return cnt6;
	}
	//----------------------------------------------------------------------
	//�����\�b�h���I�����ꂽ�Ƃ���ɒu���ƂȂȂ߂͂Ђ�����Ԃ�̂��H
	public static int checkNC (String[][] ban,int i,int j,int p){
		String[][] ban2=ban;
		int cnt4=0;
		int z=-1;
		if(p==1){
			//����
			int t=i;
			int s=j;
			for(int ul=1;ul<=7;ul++){
				t--;
				s--;
				if(t<0||s<0){
					break;
				}
				if(ban2[t][s]==" ��"){
					z=ul;
					break;
				}else if(ban2[t][s]==" �E�E"||ban2[t][s]==" ��"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				cnt4--;
				for(int ul=1;ul<=z;ul++){
					t--;
					s--;
					cnt4++;
				}
			}

			//�E��
			z=-1;
			t=i;
			s=j;
			for(int ur=1;ur<=7;ur++){
				t--;
				s++;
				if(t<0||s>=8){
					break;
				}
				if(ban2[t][s]==" ��"){
					z=ur;
					break;
				}else if(ban2[t][s]==" �E�E"||ban2[t][s]==" ��"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				cnt4--;
				for(int ur=1;ur<=z;ur++){
					t--;
					s++;
					cnt4++;
				}
			}

			//����
			z=-1;
			t=i;
			s=j;
			for(int dl=1;dl<=7;dl++){
				t++;
				s--;
				if(t>=8||s<0){
					break;
				}
				if(ban2[t][s]==" ��"){
					z=dl;
					break;
				}else if(ban2[t][s]==" �E�E"||ban2[t][s]==" ��"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				cnt4--;
				for(int dl=1;dl<=z;dl++){
					t++;
					s--;
					cnt4++;
				}
			}

			//�E��
			z=-1;
			t=i;
			s=j;
			for(int dr=1;dr<=7;dr++){
				t++;
				s++;
				if(t>=8||s>=8){
					break;
				}
				if(ban2[t][s]==" ��"){
					z=dr;
					break;
				}else if(ban2[t][s]==" �E�E"||ban2[t][s]==" ��"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				cnt4--;
				for(int dr=1;dr<=z;dr++){
					t++;
					s++;
					cnt4++;
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
				if(ban2[t][s]==" �Z"){	
					z=ul;
					break;
				}else if(ban2[t][s]==" �E�E"||ban2[t][s]==" ��"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				cnt4--;
				for(int ul=1;ul<=z;ul++){
					t--;
					s--;
					cnt4++;
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
				if(ban2[t][s]==" �Z"){
					z=ur;
					break;
				}else if(ban2[t][s]==" �E�E"||ban2[t][s]==" ��"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				cnt4--;
				for(int ur=1;ur<=z;ur++){
					t--;
					s++;
					cnt4++;
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
				if(ban2[t][s]==" �Z"){
					z=dl;
					break;
				}else if(ban2[t][s]==" �E�E"||ban2[t][s]==" ��"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				cnt4--;
				for(int dl=1;dl<=z;dl++){
					t++;
					s--;
					cnt4++;
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
				if(ban2[t][s]==" �Z"){		
					z=dr;
					break;
				}else if(ban2[t][s]==" �E�E"||ban2[t][s]==" ��"){
					z=-1;
					break;
				}
			}

			t=i;
			s=j;
			if(z!=-1){
				cnt4--;
				for(int dr=1;dr<=z;dr++){
					t++;
					s++;
					cnt4++;
				}
			}
		}

		return cnt4;
	}
	//-------------------------------------------------------------
	//�����\�b�h���u����Ƃ����������@ 1:���@2:�Z
	public static String[][] hint(String[][] ban,int p){
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(ban[i][j]==" ��"){
					ban[i][j]=" �E�E";
				}
				if(ban[i][j]!=" ��"&&ban[i][j]!=" �Z"){
					if(check(ban,i,j,p)==1){
						ban[i][j]=" ��";
					}
				}
			}
		}
		return ban;
	}
	//-------------------------------------------------------------
	//�����\�b�h���u����Ƃ����������@ 1:���@2:�Z
	public static int[] choice (String[][] ban,int p){
		//�܂��A�u����Ƃ�����s�b�N�A�b�v


		int tate=0;
		int yoko=0;
		int max=0;
		String[][] banC;
		banC=ban;
		int[] re=new int[2];

		//��ԂЂ����肩����Ƃ�
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(ban[i][j]==" ��"){
					if(checkC(ban,i,j,2)>=max){
						max=checkC(ban,i,j,2);
						tate=i;
						yoko=j;
					}
				}
			}
		}

		System.out.println("tate"+tate);
		System.out.println("yoko"+yoko);

		//�����D��
		max=0;
		for(int h=0;h<8;h++){
			for(int k=0;k<8;k++){
				if(h==0||h==7||k==0||k==7){
					if(banC[h][k]==" ��"){
						if(checkC(banC,h,k,2)>=max){
							max=checkC(banC,h,k,2);
							tate=h;
							yoko=k;
						}
					}
				}
			}
		}

		System.out.println("tate"+tate);
		System.out.println("yoko"+yoko);

		//�l����D��
		max=0;				
		for(int r=0;r<8;r+=7){
			for(int s=0;s<8;s+=7){
				if(banC[r][s]==" ��"){
					if(checkC(banC,r,s,2)>=max){
						max=checkC(banC,r,s,2);
						tate=r;
						yoko=s;
					}		
				}
			}
		}

		System.out.println("tate"+tate);
		System.out.println("yoko"+yoko);

		re[0]=tate;
		re[1]=yoko;
		return re;

	}
	//-------------------------------------------------------------
	//�����\�b�h���u����Ƃ����������@ 1:���@2:�Z
	public static int[] kimura(String[][] ban,int p){
		//����Ɏ��u�����Ȃ�
		int tate=0;
		int yoko=0;
		String[][] banC;
		banC=ban;
		int[] re2=new int[2];
		int min=10;
		int count;

		for(int n=0;n<8;n++){
			banC=ban;
			for(int m=0;m<8;m++){
				count=0;
				banC=ban;
				if(banC[n][m]==" ��"){
					banC=set(banC,n,m,2);
					banC=changeT(banC,n,m,2);
					banC=changeY(banC,n,m,2);
					banC=changeN(banC,n,m,2);
					banC=hint(banC,1);

					for(int e=0;e<8;e++){
						for(int f=0;f<8;f++){
							if(banC[e][f]==" ��"){
								count++;
							}
						}
					}

					if(count<=min){
						min=count;
						tate=n;
						yoko=m;
					}
					banC=ban;
				}
			}
		}

		System.out.println("tate"+tate);
		System.out.println("yoko"+yoko);
		
		re2[0]=tate;
		re2[1]=yoko;
		return re2;
		
	}
}


