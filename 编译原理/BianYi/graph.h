#pragma once
#ifndef GRAPH_H
#define GRAPH_H
#include<iostream>
using namespace std;
//�������ڽӱ��ʾ����Ȩ����ͼ

struct Edge 
{
	char dest;//����ߵ�ֵ
	int nextVertex;
	Edge* link;
	Edge(){}
	Edge(char c,Edge* p,int num):dest(c),link(p),nextVertex(num){}
};

struct Vertex
{
	int data;
	int final = 0;
	Edge* adj;
	bool end = false;
};

class Graph
{
private:
	int maxVertices;
	int numEdges;

public:
	Vertex* NodeTable;
	int numVertices;

	Graph()
	{
		maxVertices = 100;
		numVertices = 0;
		numEdges = 0;
		NodeTable = new Vertex[maxVertices];
		for (int i = 0; i < maxVertices; i++) {
			NodeTable[i].adj = 0;
		}
	}

	bool insertVertex();//�����
	bool insertEdge(int v1, int v2, char c);//�����

	int getFirstNeighbor(int v, char c);
	int getNextNeighbor(int v, int w, char c);

	int getValue(int i);//���ݽ����Ż�ȡ����ֵ
	int getVertexPos(int Vertex);//���ݽ���ֵ��ȡ�������

	int NumberOfVertices();
	int NumberOfEdges();

	//�����������
	void DFS(int v, char c, bool visited[]);
	void DFS(int v, char c);
};

#endif // !GRAPH_H
