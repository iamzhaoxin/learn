//#include<bits/stdc++.h>
//using namespace std;
//#define MAX 100
//struct state
//{
//    char sta;//״̬
//    string f[MAX];// ����ʽ
//    int num;//����ʽ����
//};
//struct  grammer
//{
//    string VT;//�ս��
//    string VN;//���ս��
//    struct state states[MAX];//����ʽ
//    char S;//��ʼ��
//    int tnum;//�ս������2
//    int nnum;//���ս�� ����
//    string vns;//���Ƴ��մ��ķ��ս��
//};
//struct  grammer gram;//����һ���ķ�
//string First[MAX];//first����
//string Follow[MAX];//follow����
//string FolChild[MAX];//follow[A]���Ӽ�����
//string Select[MAX][MAX];//select ���� ���ս�� ����ʽ
//int position(char a, string s);//������̬a����a������״̬���е�λ��
//void receiveGra();//�����ķ�
//void calculateFirst();//����FIRST����
//void calculateFollow();//����FOLLOW����
//void calculateSelect();//����select����
//int isallvn(string a);
//int judge();//�ж�
//void printGra();//����ķ�
//
//int main()
//{
//    receiveGra();
//    calculateFirst();
//    calculateFollow();
//    calculateSelect();
//    printGra();
//    return 0;
//}
//void receiveGra() //�����ķ�
//{
//    gram.tnum = 0;
//    gram.nnum = 0;
//    cout << "�����뿪ʼ��:" << endl;
//    cin >> gram.S;
//    cout << "���������ʽ,��#����(�մ���^����):" << endl;
//    string temp;
//    while (1)
//    {
//        cin >> temp;
//        if (temp[0] == '#')
//            break;
//        int j = position(temp[0], gram.VN);//�ҵ�״̬��λ��
//        if (j < 0) //δ���ֹ�
//        {
//            gram.VN += temp[0];
//            gram.states[gram.nnum].sta = temp[0];
//            for (int m = 3; m < temp.length(); m++)
//                gram.states[gram.nnum].f[0] += temp[m];
//            gram.states[gram.nnum].num = 1;
//
//            gram.nnum++;
//        }
//        else  //���ֹ�
//        {
//            int n = gram.states[j].num;
//            for (int m = 3; m < temp.length(); m++)
//                gram.states[j].f[n] += temp[m];
//            gram.states[j].num++;
//        }
//        if (temp[3] == '^')
//            if (position(gram.states[j].sta, gram.vns) < 0)
//                gram.vns += gram.states[j].sta;//���Ƴ��մ��ķ��ս��
//    }
//    for (int i = 0; i < gram.nnum; i++)
//    {
//        struct state st = gram.states[i];
//        for (int j = 0; j < st.num; j++)
//        {
//            string a = st.f[j];
//            for (int p = 0; p < a.length(); p++)
//            {
//                if (position(a[p], gram.VN) < 0)
//                    if (position(a[p], gram.VT) < 0) gram.VT += a[p];
//            }
//        }
//    }
//    cout << "�������!" << endl;
//
//}
//void calculateFirst() //����FIRST���� ��ÿ�����ս������first
//{
//    struct state st;
//    for (int i = 0; i < gram.nnum; i++)
//    {
//
//        int flag = 0;//��־
//        st = gram.states[i];
//        if (position(st.sta, gram.VT) < 0)
//        {
//            //	cout<<st.sta<<endl;
//            for (int j = 0; j < st.num; j++)
//            {
//                string temp = st.f[j];
//
//                if (position(temp[0], gram.VT) != -1) //�����ս��
//                {
//                    if (position(temp[0], First[i]) < 0)
//                    {
//                        First[i] += temp[0];
//                        flag = 1;
//                    }
//                }
//                else
//                {
//                    int count = 0;
//                    for (int p = 0; p < temp.length(); p++)
//                    {
//                        if (position(temp[p], gram.VT) != -1) break;
//                        //A->BC
//                        int n = position(temp[p], gram.VN);
//                        for (int k = 0; k < First[n].length(); k++)
//                        {
//                            if (First[n][k] != '^')
//                            {
//                                if (position(First[n][k], First[i]) < 0)
//                                {
//                                    First[i] += First[n][k];
//                                    flag = 1;
//                                }
//
//                            }
//                            else
//                            {
//                                count++;
//                            }
//                        }
//                        if (position('^', First[n]) < 0) break;
//                    }
//                    if (count == temp.length() && position('^', First[i]) < 0)
//                    {
//                        First[i] += '^';
//                        flag = 1;
//                    }
//
//                }
//            }
//            if (flag)
//            {
//                i = -1;
//            }
//        }
//
//    }
//}
//void calculateFollow() //����FOLLOW����
//{
//    Follow[0] += '#';
//    for (int i = 0; i < gram.nnum; i++)
//    {
//        FolChild[i][0] = '#';
//    }
//    int flag;
//    do
//    {
//        flag = 0;//���ϲ��ٱ仯�ͽ���ѭ��
//        for (int i = 0; i < gram.nnum; i++)
//        {
//            struct state st = gram.states[i];
//            for (int j = 0; j < st.num; j++)
//            {
//                string sc = st.f[j];
//                int count = 0;
//                for (; count < sc.length(); count++) //����ÿһ������ʽ�е�Ԫ��
//                {
//                    if (position(sc[count], gram.VN) != -1)  //�������ս������
//                    {
//                        if (count < sc.length()) //���ڷ��ս��
//                        {
//                            int n = position(sc[count], gram.VN);//�õ�������ս���ŵ�Follow�����
//                            if (count == sc.length() - 1) //��A->aB������� ��Follow[n]=Follow[i]
//                            {
//                                for (int p = 0; p < Follow[i].length(); p++)
//                                {
//                                    if (position(Follow[i][p], Follow[n]) < 0) //Follow[n]��û��
//                                    {
//                                        Follow[n] += Follow[i][p];
//                                        flag = 1;
//                                    }
//                                }
//                                if (position(i + 48, FolChild[n]) < 0)
//                                {
//                                    FolChild[n] += i + 48;
//                                }
//
//
//                            }
//                            else  //��A->aB?�������
//                            {
//                                if (position(sc[count + 1], gram.VT) != -1) //��A->aBb�������
//                                {
//                                    if (position(sc[count + 1], Follow[n]) < 0) //Follow[n]��û��
//                                    {
//                                        Follow[n] += sc[count + 1];
//                                        flag = 1;
//                                    }
//                                }
//                                else  //��A->aBCD�������
//                                {
//                                    int ns = position(sc[count + 1], gram.VN);
//                                    if (position(i + 48, FolChild[ns]) < 0)
//                                    {
//                                        FolChild[ns] += i + 48;
//                                    }
//                                    for (int p = 0; p < First[ns].length(); p++)
//                                    {
//                                        if (position(First[ns][p], Follow[n]) < 0 && First[ns][p] != '^')
//                                        {
//                                            Follow[n] += First[ns][p];
//                                            flag = 1;
//                                        }
//                                    }
//                                    if (position('^', First[ns]) != -1) //��ʱFo[n]+=Fo[i]
//                                    {
//                                        for (int p = 0; p < Follow[i].length(); p++)
//                                        {
//                                            if (position(Follow[i][p], Follow[n]) < 0) //Follow[n]��û��
//                                            {
//                                                Follow[n] += Follow[i][p];
//                                                flag = 1;
//                                            }
//                                        }
//                                        if (position(i + 48, FolChild[n]) < 0)
//                                        {
//                                            FolChild[n] += i + 48;
//                                        }
//
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//
//            }
//
//        }
//
//
//    }     while (flag);
//    do //��Follow[A]���Ӽ��ϲ���Follow[A]��
//    {
//        flag = 0;
//        for (int i = 0; i < gram.nnum; i++)
//        {
//            if (FolChild[i][0] != '#')
//            {
//                cout << FolChild[i] << endl;
//                for (int j = 0; j < FolChild[i].length(); j++)
//                {
//                    int n = FolChild[i][j] - 48;//�Ӽ���Follow���
//                    for (int p = 0; p < Follow[n].length(); p++)
//                    {
//                        if (position(Follow[n][p], Follow[i]) < 0)
//                        {
//                            Follow[i] += Follow[n][p];
//                            flag = 1;
//                        }
//                    }
//                }
//            }
//
//
//        }
//    }     while (flag);
//}
//
//int position(char a, string s) //������̬a����a������״̬���е�λ��
//{
//    for (int i = 0; i < s.length(); i++)
//    {
//        if (s[i] == a) return i;
//    }
//    return -1;
//}
//void calculateSelect() //����select���� string Select[MAX][MAX]���� ���ս�� ����ʽ
//{
//    for (int i = 0; i < gram.nnum; i++)
//    {
//        struct state st = gram.states[i];
//        for (int j = 0; j < st.num; j++)
//        {
//            string temp = st.f[j];
//            if (position(temp[0], gram.VT) != -1 && temp[0] != '^') //A->a..
//            {
//                Select[i][j] += temp[0];
//            }
//            else if (temp[0] == '^')  //A->$ select(A->$)=follow(A)
//            {
//                Select[i][j] = Follow[i];
//            }
//            else  //A->B...
//            {
//                int isa = isallvn(temp);
//                if (isa == temp.length())
//                {
//                    for (int p = 0; p < Follow[i].length(); p++)
//                    {
//                        if (position(Follow[i][p], Select[i][j]) < 0)
//                        {
//                            Select[i][j] += Follow[i][p];
//                        }
//                    }
//                    isa--;
//                }
//                for (int k = 0; k <= isa; k++)
//                {
//                    int n = position(temp[k], gram.VN);
//                    for (int p = 0; p < First[n].length(); p++)
//                    {
//                        if (position(First[n][p], Select[i][j]) < 0 && First[n][p] != '^')
//                            Select[i][j] += First[n][p];
//                    }
//                }
//
//            }
//        }
//    }
//}
//int isallvn(string a)
//{
//    int flag = 1;
//    for (int i = 0; i < a.length(); i++)
//    {
//        if (position(a[i], gram.VT) != -1)
//        {
//            flag = 0;
//            break;
//        }
//    }
//    int i = 0;
//    if (flag)
//    {
//        for (; i < a.length(); i++)
//        {
//            int n = position(a[i], gram.VN);
//            if (position('^', First[n]) == -1)
//            {
//                return i;//First�в�����$
//            }
//        }
//    }
//    return i;
//}
//int judge() //�ж�
//{
//    for (int i = 0; i < gram.nnum; i++)
//    {
//        string str;
//        for (int j = 0; j < gram.states[i].num; j++)
//        {
//            str += Select[i][j];
//        }
//        for (int p = 0; p < str.length(); p++)
//        {
//            for (int m = p + 1; m < str.length(); m++)
//            {
//                if (str[p] == str[m])
//                {
//                    return 0;
//                }
//            }
//        }
//
//    }
//    return 1;
//}
//void printGra() //����ķ�
//{
//    cout << "��̬����:" << gram.S << endl;
//    cout << "ӳ���ϵ:" << endl;
//    for (int i = 0; i < gram.nnum; i++)
//    {
//        struct state st = gram.states[i];
//        for (int j = 0; j < st.num; j++)
//        {
//            cout << st.sta << "->" << st.f[j] << endl;
//        }
//    }
//    cout << "�ս��:" << gram.VT << endl;
//    cout << "���ս��:" << gram.VN << endl;
//    cout << "���Ƴ��մ��ķ��ս��:" << gram.vns << endl;
//    cout << "FIRST��������" << endl;
//    for (int i = 0; i < gram.nnum; i++)
//    {
//        cout << gram.states[i].sta << " " << First[i] << endl;
//    }
//    cout << "FOLLOW��������" << endl;
//    for (int i = 0; i < gram.nnum; i++)
//    {
//        cout << gram.states[i].sta << " " << Follow[i] << endl;
//    }
//    cout << "Select��������" << endl;
//    for (int i = 0; i < gram.nnum; i++)
//    {
//        struct state st = gram.states[i];
//        for (int j = 0; j < st.num; j++)
//        {
//            cout << "Select(" << st.sta << "->" << st.f[j] << ") = " << Select[i][j] << endl;
//        }
//    }
//    if (judge()) cout << "��LL(1)�ķ�" << endl;
//    else cout << "����LL(1)�ķ�" << endl;
//}
///*
//E
//E->TA
//A->+TA
//A->$
//T->FB
//B->*FB
//B->$
//F->(E)
//F->i
//#
//*/
