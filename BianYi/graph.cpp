#include "graph.h"
#include <iostream>
using namespace std;

bool Graph::insertVertex()
{
	if (numVertices == maxVertices)
		return false;
	NodeTable[numVertices].data = numVertices;
	numVertices++;//������numVertices�ǽڵ����˳��ı��
	return true;
}

bool Graph::insertEdge(int v1, int v2, char c)
{
	if (v1 > -1 && v1<numVertices && v2>-1 && v2 < numVertices)
	{
		Edge* p = NodeTable[v1].adj;
		while (p != NULL && !((p->nextVertex == v2) && (p->dest == c)))//��ʱ������ͼ�д��ڶ����ߣ����ߵ�ֵ�����ظ�
			p = p->link;
		if (p != NULL) 
			return false;//˵����ʱv1 v2֮��ı��Ѵ���

		p = new Edge(c, NodeTable[v1].adj, v2);
		NodeTable[v1].adj = p;

		numEdges++;
		return true;
	}
	return false;
}

int Graph::getFirstNeighbor(int v, char c)
{
	Edge* p = NodeTable[v].adj;
	while (p != NULL && p->dest != c)
		p = p->link;

	if (p != NULL)
		return p->nextVertex;
	else
		return -1;
}

int Graph::getNextNeighbor(int v, int w, char c)
{
	if (v > -1 && v < numVertices)
	{
		Edge* p = NodeTable[v].adj;
		while (p != NULL && p->nextVertex != w)
			p = p->link;
		p = p->link;
		while (p != NULL && p->dest != c)
			p = p->link;

		if (p == NULL)
			return -1;
		else
			return p->nextVertex;
	}
	return -1;
}

//���ݽ����Ż�ȡ����ֵ
int Graph::getValue(int i) {
	return i >= 0 && i <= numVertices ? NodeTable[i].data : -1;
}

//���ݽ���ֵ��ȡ�������
int Graph::getVertexPos(int Vertex) {
	for (int i = 0; i < numVertices; i++)
	{
		if (NodeTable[i].data == Vertex)
			return i;
	}
	return -1;
}

int Graph::NumberOfVertices()
{
	return numVertices;
}

int Graph::NumberOfEdges()
{
	return numEdges;
}

void Graph::DFS(int v, char c, bool visited[])
{
	cout << this->getValue(v) << " ";
	visited[v] = true;

	int w = this->getFirstNeighbor(v, c);
	while (w != -1)
	{
		if (visited[w] == false)
			DFS(w, c, visited);

		w = this->getNextNeighbor(v, w, c);
	}
}

void Graph::DFS(int v, char c)
{
	int i, loc, n = this->NumberOfVertices();
	bool* visited = new bool[n];
	for (i = 0; i < n; i++)
		visited[i] = false;
	loc = this->getVertexPos(v);

	DFS(loc, c, visited);

	delete[]visited;
}
