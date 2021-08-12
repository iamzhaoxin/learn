#pragma once
#ifndef NFAMANAGER_H
#define NFAMANAGER_H
#include<iostream>
#include<stack>
#include<vector>
#include<set>
#include<map>
#include"graph.h"
using namespace std;

class nfaManager {
private:
	stack<int> st;
    Graph nfa;
    Graph dfa;
    Graph mini_dfa;//��С��dfa
    vector<char> chars;
    int NumOfChar;
    int start_state;//����nfa�Ŀ�ʼ���
    int start_state_dfa;//����mini_dfa�Ŀ�ʼ���

public:
    nfaManager();

    //regexp->nfa

    //������ʽ�������ӷ���.��
    string insert_concat(string regexp);
    int priority(char c);
    string regexp_to_postfix(string regexp);
    void character(char c);
    void union_();//����ѡ��
    void concatenation();//��������
    void kleene_star();//�հ�
    void postfix_to_nfa(string postfix);
    void show_nfa();


    //nfa -> dfa
    void getNeighbor(int v, char c, set<int>& di);
    void epsilon_closure(int state, set<int>& si);
    void nfa_to_dfa(set<int>& si);
    void show_dfa();

    //��С��dfa
    int dfa_transform(int v, char c, map<int, vector<int>> mp_1);
    bool if_equal(int v1, int v2, vector<char> chars, map<int, vector<int>> mp_1); //�˺��������ж�����dfa����״̬ת�����Ƿ���ͬ
    void minimize_dfa();
    void show_mini_dfa();

    //����С��dfa���ɶ�Ӧ�Ĵʷ���������C����������
    void getCcode(int v, vector<string>& lines);
    void show_code();
};


#endif // !NFAMANAGER_H
