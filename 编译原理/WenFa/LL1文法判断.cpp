//#include<bits/stdc++.h>
//using namespace std;
//#define MAX 100
//struct state
//{
//    char sta;//状态
//    string f[MAX];// 产生式
//    int num;//产生式个数
//};
//struct  grammer
//{
//    string VT;//终结符
//    string VN;//非终结符
//    struct state states[MAX];//产生式
//    char S;//开始符
//    int tnum;//终结符个数2
//    int nnum;//非终结符 个数
//    string vns;//能推出空串的非终结符
//};
//struct  grammer gram;//定义一个文法
//string First[MAX];//first集合
//string Follow[MAX];//follow集合
//string FolChild[MAX];//follow[A]的子集序列
//string Select[MAX][MAX];//select 集合 非终结符 产生式
//int position(char a, string s);//根据终态a查找a在有限状态集中的位置
//void receiveGra();//接收文法
//void calculateFirst();//计算FIRST集合
//void calculateFollow();//计算FOLLOW集合
//void calculateSelect();//计算select集合
//int isallvn(string a);
//int judge();//判断
//void printGra();//输出文法
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
//void receiveGra() //接受文法
//{
//    gram.tnum = 0;
//    gram.nnum = 0;
//    cout << "请输入开始符:" << endl;
//    cin >> gram.S;
//    cout << "请输入产生式,以#结束(空串以^代替):" << endl;
//    string temp;
//    while (1)
//    {
//        cin >> temp;
//        if (temp[0] == '#')
//            break;
//        int j = position(temp[0], gram.VN);//找到状态的位置
//        if (j < 0) //未出现过
//        {
//            gram.VN += temp[0];
//            gram.states[gram.nnum].sta = temp[0];
//            for (int m = 3; m < temp.length(); m++)
//                gram.states[gram.nnum].f[0] += temp[m];
//            gram.states[gram.nnum].num = 1;
//
//            gram.nnum++;
//        }
//        else  //出现过
//        {
//            int n = gram.states[j].num;
//            for (int m = 3; m < temp.length(); m++)
//                gram.states[j].f[n] += temp[m];
//            gram.states[j].num++;
//        }
//        if (temp[3] == '^')
//            if (position(gram.states[j].sta, gram.vns) < 0)
//                gram.vns += gram.states[j].sta;//能推出空串的非终结符
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
//    cout << "输入结束!" << endl;
//
//}
//void calculateFirst() //计算FIRST集合 对每个非终结符计算first
//{
//    struct state st;
//    for (int i = 0; i < gram.nnum; i++)
//    {
//
//        int flag = 0;//标志
//        st = gram.states[i];
//        if (position(st.sta, gram.VT) < 0)
//        {
//            //	cout<<st.sta<<endl;
//            for (int j = 0; j < st.num; j++)
//            {
//                string temp = st.f[j];
//
//                if (position(temp[0], gram.VT) != -1) //遇到终结符
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
//void calculateFollow() //计算FOLLOW集合
//{
//    Follow[0] += '#';
//    for (int i = 0; i < gram.nnum; i++)
//    {
//        FolChild[i][0] = '#';
//    }
//    int flag;
//    do
//    {
//        flag = 0;//集合不再变化就结束循环
//        for (int i = 0; i < gram.nnum; i++)
//        {
//            struct state st = gram.states[i];
//            for (int j = 0; j < st.num; j++)
//            {
//                string sc = st.f[j];
//                int count = 0;
//                for (; count < sc.length(); count++) //遍历每一个产生式中的元素
//                {
//                    if (position(sc[count], gram.VN) != -1)  //遇到非终结符结束
//                    {
//                        if (count < sc.length()) //存在非终结符
//                        {
//                            int n = position(sc[count], gram.VN);//得到这个非终结符号的Follow集编号
//                            if (count == sc.length() - 1) //是A->aB这种情况 则Follow[n]=Follow[i]
//                            {
//                                for (int p = 0; p < Follow[i].length(); p++)
//                                {
//                                    if (position(Follow[i][p], Follow[n]) < 0) //Follow[n]中没有
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
//                            else  //是A->aB?这种情况
//                            {
//                                if (position(sc[count + 1], gram.VT) != -1) //是A->aBb这种情况
//                                {
//                                    if (position(sc[count + 1], Follow[n]) < 0) //Follow[n]中没有
//                                    {
//                                        Follow[n] += sc[count + 1];
//                                        flag = 1;
//                                    }
//                                }
//                                else  //是A->aBCD这种情况
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
//                                    if (position('^', First[ns]) != -1) //此时Fo[n]+=Fo[i]
//                                    {
//                                        for (int p = 0; p < Follow[i].length(); p++)
//                                        {
//                                            if (position(Follow[i][p], Follow[n]) < 0) //Follow[n]中没有
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
//    do //将Follow[A]的子集合并到Follow[A]中
//    {
//        flag = 0;
//        for (int i = 0; i < gram.nnum; i++)
//        {
//            if (FolChild[i][0] != '#')
//            {
//                cout << FolChild[i] << endl;
//                for (int j = 0; j < FolChild[i].length(); j++)
//                {
//                    int n = FolChild[i][j] - 48;//子集的Follow编号
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
//int position(char a, string s) //根据终态a查找a在有限状态集中的位置
//{
//    for (int i = 0; i < s.length(); i++)
//    {
//        if (s[i] == a) return i;
//    }
//    return -1;
//}
//void calculateSelect() //计算select集合 string Select[MAX][MAX]集合 非终结符 产生式
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
//                return i;//First中不含有$
//            }
//        }
//    }
//    return i;
//}
//int judge() //判断
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
//void printGra() //输出文法
//{
//    cout << "初态如下:" << gram.S << endl;
//    cout << "映射关系:" << endl;
//    for (int i = 0; i < gram.nnum; i++)
//    {
//        struct state st = gram.states[i];
//        for (int j = 0; j < st.num; j++)
//        {
//            cout << st.sta << "->" << st.f[j] << endl;
//        }
//    }
//    cout << "终结符:" << gram.VT << endl;
//    cout << "非终结符:" << gram.VN << endl;
//    cout << "能推出空串的非终结符:" << gram.vns << endl;
//    cout << "FIRST集合如下" << endl;
//    for (int i = 0; i < gram.nnum; i++)
//    {
//        cout << gram.states[i].sta << " " << First[i] << endl;
//    }
//    cout << "FOLLOW集合如下" << endl;
//    for (int i = 0; i < gram.nnum; i++)
//    {
//        cout << gram.states[i].sta << " " << Follow[i] << endl;
//    }
//    cout << "Select集合如下" << endl;
//    for (int i = 0; i < gram.nnum; i++)
//    {
//        struct state st = gram.states[i];
//        for (int j = 0; j < st.num; j++)
//        {
//            cout << "Select(" << st.sta << "->" << st.f[j] << ") = " << Select[i][j] << endl;
//        }
//    }
//    if (judge()) cout << "是LL(1)文法" << endl;
//    else cout << "不是LL(1)文法" << endl;
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
