import java.util.*;
public class Heap {
	public HeapNode heapMax;// pointer to the max node
	int noOfNodes;// maintains the no of nodes
	Hashtable<String,HeapNode> ht = new Hashtable<String,HeapNode>();// hash-table with the word as key and pointer to the node as value
	int noOfTopLevel;
	List<HeapNode> ptrList = new ArrayList<HeapNode>();
	public boolean baba = false;
	public Heap(String s,int frequency){//Constructor for the heap by adding the first node
		HeapNode h = new HeapNode(s,frequency);
		ht.put(s, h);
		this.heapMax = h;
		this.noOfNodes=1;
		noOfTopLevel=1;
		this.heapMax.isMaxNode = true;
		heapMax.leftSibling = heapMax;
		heapMax.rightSibling = heapMax;
		heapMax.parent = null;
		heapMax.child = null;
		//System.out.println(h.word +" inserted with "+ h.frequency);
		//ht.put(s, h);
		//System.out.println("heap created");
		
	}
//*********************************INSERT*********************************************************************************
		public void insert(String s,int frequency){
//if heap is completely empty
			baba = false;
			if(heapMax==null){
				HeapNode h = new HeapNode(s,frequency);
				ht.put(s, h);
				this.heapMax = h;
				this.noOfNodes=1;
				noOfTopLevel=1;
				this.heapMax.isMaxNode = true;
				heapMax.leftSibling = heapMax;
				heapMax.rightSibling = heapMax;
				heapMax.parent = null;
				heapMax.child = null;	
				return;			
			}	
//check if it exists in the hash table/heap already
			if(ht.containsKey(s)){// if the word already exists in the heap
				HeapNode h = ht.get(s);//get the node's pointer using hash-table
				//System.out.println(h.word+h.frequency+" This already exists in the hash table");
				increaseKey(h,frequency);//call increase key to increase the frequency of the word that already exixts
				return;
				//System.out.println(h.word+h.frequency+" After the increase");
			}
//else make a new node and insert it
			else{
				noOfTopLevel++;
				noOfNodes++;
				HeapNode h = new HeapNode(s,frequency);//new node
				h.rightSibling = heapMax.rightSibling;//h's right node pointer to heapmax's right node
				h.leftSibling = heapMax;//h's left node pointer
				heapMax.rightSibling.leftSibling = h;//heapMax's right node's left pointer 
				heapMax.rightSibling = h;//right pointer of heapMax.
				h.parent = null;
				h.isTopLevel=true;
				h.childCut=false;
				checkWithMax(h);
				ht.put(s, h);
				//System.out.println(h.word +" inserted with "+ h.frequency);
				//System.out.println("no of top level after you insert"+noOfTopLevel);
				return;
				//System.out.println("hash table entry"+(ht.get(s)).word+(ht.get(s)).frequency);
				//
			}
			//return;
		}
//*********************************INCREASE KEY*********************************************************************************
		public void increaseKey(HeapNode h, int frequency){
			h.frequency=h.frequency+frequency;
			
//for top level nodes i.e. without any childcut logic
			if((h.isTopLevel==true)||(h.parent==null)){
				//System.out.println(h.word +" Top level increase "+ h.frequency);
				checkWithMax(h);	
				return;
			}
			
//for childcut logic
			//if((h.frequency>h.parent.frequency)||(h.parent.childCut==true)){-----------------1
			if((h.frequency>h.parent.frequency)){
				cascadeCut(h);
				noOfTopLevel++;
				HeapNode hp = h.parent;
				while(hp.childCut==true && (hp.parent!=null)){
					cascadeCut(hp);
					noOfTopLevel++;
					HeapNode hpp = hp;
					hp=hp.parent;
					hpp.parent = null;
					
				}
				if(hp.parent!=null){
					hp.childCut=true;
				}
				if(h.parent==null){
					
				}
				checkWithMax(h);
				h.parent=null;
			}
			
		}
		
//*********************************REMOVE MAX*********************************************************************************
		
		public HeapNode removeMax(){
			HeapNode deletedMax = heapMax;	
			//System.out.println("no of top level when you start removemax"+noOfTopLevel);
			//System.out.println("going to remove"+deletedMax.word+deletedMax.frequency);
			ht.remove(deletedMax.word);
			if(noOfNodes==1){
				heapMax=null;//make it null
				noOfNodes--;//decrease i.e obv=0
				noOfTopLevel--;
				return deletedMax;				
			}
			noOfTopLevel--;
			if(deletedMax.degree>0){
				HeapNode childOfMax = heapMax.child;
				HeapNode rightOfMax = heapMax.rightSibling;
				HeapNode leftOfMax = heapMax.leftSibling;
				if((rightOfMax.rightSibling==deletedMax)&&(rightOfMax!=deletedMax)){//Check if one element other than deletedmax exists
					rightOfMax.rightSibling=childOfMax;
					rightOfMax.leftSibling = childOfMax.leftSibling;
				}
				else if(rightOfMax==deletedMax){//check if right of max is heapmax and child is only one
					rightOfMax = childOfMax;
				}
				
				else if((leftOfMax.leftSibling==deletedMax)&&(leftOfMax!=deletedMax)){//Check if 
					leftOfMax.leftSibling = childOfMax.leftSibling;
					leftOfMax.rightSibling = childOfMax;
				}
				
				else if(leftOfMax==deletedMax){//check if right of max is heapmax and child is only one
					leftOfMax = childOfMax;
				}
				//childOfMax changes
				childOfMax.leftSibling = leftOfMax;	
				leftOfMax.rightSibling = childOfMax;
				int numOfChildren = deletedMax.degree;
				//System.out.println(" no of children "+numOfChildren);
				heapMax = childOfMax;//assign child as heapmax temporarily
				
				heapMax.isMaxNode=true;
				//System.out.println(deletedMax.word+deletedMax.frequency);
				//System.out.println(heapMax.word+heapMax.frequency);
				//System.out.println(deletedMax.child.word+deletedMax.child.frequency);
				//children of heapmax changes
				while(numOfChildren>0){
					//System.out.println(" no of children in while loop "+numOfChildren);
					childOfMax.parent = null;
					childOfMax.isTopLevel = true;
					childOfMax.childCut = false;				
					if(numOfChildren==1){
						childOfMax.rightSibling = rightOfMax;
						rightOfMax.leftSibling = childOfMax;
						//System.out.println(childOfMax.rightSibling.frequency+"pls tell me more");
						//System.out.println(childOfMax.leftSibling.frequency+"pls tell me more");
	/*					if(noOfTopLevelatStart1==1){
							childOfMax.rightSibling=heapMax;//last child of heapmax changes
							heapMax.leftSibling = childOfMax;						
						}*/					
					}
					childOfMax=childOfMax.rightSibling;
					noOfTopLevel++;
					numOfChildren--;
				}	
				
				/*
				if(deletedMax.leftSibling==deletedMax){
					int numOfChildren = deletedMax.degree;
					HeapNode child = deletedMax.child;
					while(numOfChildren>0){
						child.childCut = false;
						child.isTopLevel = true;
						child.parent = null;
						child = child.rightSibling;
						numOfChildren--;
						noOfTopLevel++;
					}
					
				}
				else{
					int numOfChildren = deletedMax.degree;
					HeapNode child = deletedMax.child;
					while(numOfChildren>0){
						child.childCut = false;
						child.isTopLevel = true;
						child.parent = null;
						child = child.rightSibling;
						numOfChildren--;
						noOfTopLevel++;
					}
					deletedMax.child.leftSibling.rightSibling = deletedMax.rightSibling;
					deletedMax.rightSibling.leftSibling = deletedMax.child.leftSibling;
					
					deletedMax.child.leftSibling = deletedMax.leftSibling;
					deletedMax.leftSibling.rightSibling = deletedMax.child;
					
				}
				heapMax.isMaxNode=false;
				heapMax = deletedMax.child;
				heapMax.isMaxNode=true;*/
				
			}
			//if heapMax does not have children
			else{
				//System.out.println("no of top level before sibling borrowing"+noOfTopLevel);
				deletedMax.leftSibling.rightSibling = deletedMax.rightSibling;
				deletedMax.rightSibling.leftSibling = deletedMax.leftSibling;
				heapMax=deletedMax.rightSibling;//assign rightsibling of heapmax as new max temporarily
				heapMax.isMaxNode=true;
				
				//System.out.println("label01"+deletedMax.word+deletedMax.frequency);
				//System.out.println("label01"+heapMax.word+heapMax.frequency);
			}
			//and it starts to merge all the trees with same degree
			ptrList.add(heapMax);
			HeapNode topNode = heapMax.rightSibling;
			HeapNode topNodeNext = topNode.rightSibling;
			noOfNodes--;
			
			int noOfTopLevelAtStart = noOfTopLevel;
			//System.out.println("no of top level at start"+noOfTopLevel);
			for(int i=0;i<noOfTopLevelAtStart-1;i++){
				if(baba==true){
					//baba = false;
					break;
				}
				//System.out.println("check1**********************************************************");
				//System.out.println("loop number"+i);
				//System.out.println(topNode.word+topNode.frequency);
				//System.out.println("no of top level before combine back"+noOfTopLevel);
				combineBack(topNode);
				//System.out.println("no of top level after combine back"+noOfTopLevel);
				topNode = topNodeNext;
				topNodeNext = topNodeNext.rightSibling;

					
				
			}
			topNode = heapMax.rightSibling;
			//System.out.println("checkfinal");
			
			//System.out.println(heapMax.word+heapMax.frequency);
			
			//System.out.println(heapMax.rightSibling.word+heapMax.rightSibling.frequency);
			//System.out.println("no of top level before changing max"+noOfTopLevel);
			for(int i=0;i<noOfTopLevel;i++){
				checkWithMax(topNode);				
				topNode=topNode.rightSibling;
			}
			//System.out.println("new heapmax "+heapMax.word+heapMax.frequency);
			//System.out.println("new heapmax ka right "+heapMax.rightSibling.word+heapMax.rightSibling.frequency);
			ptrList.clear();
			//System.out.println("__________________________________________________________________");
			
			return deletedMax;
		}
//********************************CHECK WITH MAX*********************************************************
		public void checkWithMax(HeapNode h){
			if(h.frequency>heapMax.frequency){// check if frequency of the new node is more than the frequency of the max node
				heapMax.isMaxNode = false;// if true then heapMax as max element is made false
				heapMax = h;// the new node becomes max node
				heapMax.isMaxNode = true;// the new node becomes max
				//System.out.println(heapMax.word+heapMax.frequency+" is heapMax");
				//System.out.println("new king in the house");
			}
		}
//********************************CASCADECUT*********************************************************
		public void cascadeCut(HeapNode h){
			h.parent.degree--;//parent's degree goes down by 1
			h.isTopLevel=true;// is top level is made true
			h.childCut=false;//childcut not needed for top level nodes
			//System.out.println("cascadecut on"+h.word+h.frequency+h.childCut+" now parent "+h.parent.word+h.parent.frequency+h.parent.childCut);
			//remove node from exisiting position
			if(h.parent.degree!=0){
				h.rightSibling.leftSibling = h.leftSibling;
				h.leftSibling.rightSibling = h.rightSibling;
				if(h.parent.child==h){
					h.parent.child = h.rightSibling;
				}
			}
			else{//no children left for the parent 
				h.parent.child=null;
			}
			/*attach at top level*/
			h.leftSibling=heapMax;
			h.rightSibling = heapMax.rightSibling;
			heapMax.rightSibling.leftSibling = h;
			heapMax.rightSibling=h;
			//noOfTopLevel++;
			//other changes
			h.childCut = false;
			h.isTopLevel = true;
		
		}
//*****************************COMBINE BACK***********************************************************
		public void combineBack(HeapNode h){//recursive
			//if empty list then add the node and return
			if(ptrList.isEmpty()){
				ptrList.add(h);
				return;
			}
			//look in the arraylist and combine if degree is same
			for(HeapNode j:ptrList){
				//System.out.println("letscheckdegree");
				//System.out.println(j.degree+" vv "+j.frequency+j.word);
				//System.out.println(h.degree+" vv "+h.frequency+h.word);
				//System.out.println(h.rightSibling.frequency+h.rightSibling.word);
				//System.out.println(h.rightSibling.leftSibling.frequency+h.rightSibling.leftSibling.word);
				//if(h.degree>0)
					//System.out.println(h.child.word+h.child.frequency);
				//System.out.println("checkoverdegree");	
				if(h==j){
					baba = true;
					return;
					//b = true;
				}
				if(j.degree==h.degree){
					if(j.frequency>=h.frequency){
						//System.out.println("letscheck");
						//System.out.println(j.frequency);
						//System.out.println(h.frequency);
						//System.out.println("checkover");
						if(h.isMaxNode==true){
							heapMax=j;
							j.isMaxNode=true;
							h.isMaxNode=false;
						}
						h.parent = j;
						h.isTopLevel = false;
						//System.out.println("h siblings");
						//System.out.println(h.leftSibling.word+h.leftSibling.frequency);
						//System.out.println(h.rightSibling.word+h.rightSibling.frequency);
						h.leftSibling.rightSibling = h.rightSibling;
						h.rightSibling.leftSibling = h.leftSibling;
						
						if(j.child!=null){
							h.rightSibling = j.child;
						}
						else{
							h.rightSibling=h;
						}
						if(j.child!=null){
							//System.out.println(j.child.frequency+"childss1");
							h.leftSibling = j.child.leftSibling;
							//System.out.println(j.child.rightSibling.frequency+"childss2");
							if(j.child.leftSibling!=null){
								j.child.leftSibling.rightSibling = h;
								//System.out.println(j.child.rightSibling.frequency+"childss3");
							}					
							j.child.leftSibling = h;
							//System.out.println(j.child.rightSibling.frequency+"childss5");
						}
						else{
							h.leftSibling = h;
							//System.out.println(j.child.rightSibling+"childss");
						}
						h.childCut = false;
						j.child=h;
						//System.out.println(j.child.rightSibling.frequency+"childss4");
						j.degree++;
						noOfTopLevel--;
						ptrList.remove(j);
						combineBack(j);
						return;
					}
					else{
						if(j.isMaxNode==true){
							//System.out.println("lavda bc");
							heapMax=h;
							h.isMaxNode=true;
							j.isMaxNode=false;
							
						}
						//System.out.println("letscheck2");
						//System.out.println(j.frequency);
						//System.out.println(h.frequency);
						//System.out.println("checkover2");
						j.parent = h;
						j.isTopLevel = false;
						//System.out.println(j.leftSibling.rightSibling.frequency+"waow");
						//System.out.println(j.rightSibling.leftSibling.frequency+"waow");
						j.leftSibling.rightSibling = j.rightSibling;
						//System.out.println(j.leftSibling.rightSibling.frequency+"waow");
						j.rightSibling.leftSibling = j.leftSibling;							
						j.rightSibling = h.child;
						if(h.child!=null){
							j.rightSibling = h.child;
						}
						else{
							j.rightSibling=j;
						}
						if(h.child!=null){
							j.leftSibling = h.child.leftSibling;
							if(h.child.leftSibling!=null){
								h.child.leftSibling.rightSibling = j;
							}						
							h.child.leftSibling = j;
						}
						else{
							j.leftSibling = j;
						}
						h.child = j;
						j.childCut = false;
						h.degree++;
						noOfTopLevel--;
						ptrList.remove(j);
						combineBack(h);
						return;
						//ptrList.add(h);
					}				
				}//if ends
			}//for ends
			//System.out.println("checkover4");
			ptrList.add(h);
			return;	
			//return false;
		}
}
