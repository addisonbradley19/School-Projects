/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author addis
 */
//course: CSC310
//project name: HW8
//date: 11/7/2016
//author: Addison Bradley
//purpose: This program implements basic heap functions such as printing a heap, adding a list of numbers, inserting the a number into the heap, searching for a number in the heap and deleting the largest value in the heap.
import java.util.Scanner;
public class CSC310HW8 {
    static class heap{
        private int[] arrayOfHeap;      //the array that carries all values in the heap
        private int maxSize;            //the highest the heap can go
        private int currentSize;        //the current size of the heap
        
        public heap(int max){           //heap creation
            maxSize = max;              //sets the user submitted max size to be the largest the heap can go
            currentSize = 0;            //sets current size to 0 because currently we have no values in the heap
            arrayOfHeap = new int[maxSize];     //creates a new array to hold the heap that is the size of the max 
        }
        
        public boolean isEmpty(){
            return currentSize == 0;            //checks to see if the heap is empty
        }
        
        public boolean insert(int key){         //inserts the user chosen value into the heap
            if(currentSize == maxSize)          //if the heap is at it's largest value then we don't add any more
                return false;                                 
            arrayOfHeap[currentSize] = key;     //adds the user submitted key into the array at the spot that is the current size, which will be changed here in a second.
            trickleUp(currentSize++);             //change the current heap so that the new addition is in it's appropriate spot. increments the size of the array because we have just added a value so the visual tree would be larger
            return true;                        //return true because you achieved what you set out to do
        }
        
        public void trickleUp(int index){           //
            int idxOfParent = (index-1)/2;          //the index of the parent which the current child minus one divided by two which is close to the math for finding the child of the parent
            int insertNumber = arrayOfHeap[index];  //the number we have just inserted into the heap that we now want to bring further up the heap
            
            while(index > 0 && arrayOfHeap[idxOfParent] < insertNumber){        //while the current spot in the array is not zero, we will do the following actions inside. We also see if the current parent at the index is of a smaller value than the current child
                    arrayOfHeap[index] = arrayOfHeap[idxOfParent];      //if so change the current value at the index position into the 
                    index = idxOfParent;                                //change the current index spot in the array to the index of the parent so that when the index goes further up heap and continues to find the right spot for the inserted value
                    idxOfParent = (idxOfParent-1)/2;                    //changes the value of the index of parent into that of our new index by using the equation as before
            }
            arrayOfHeap[index] = insertNumber;          //do one final switch with the current index by instead putting the insert number
        }
        
        public boolean deleteLargest(){
            if(isEmpty()){                  //the heap is empty, do not attempt to add a delete anything
                return false;
            }
            arrayOfHeap[0] = arrayOfHeap[currentSize-1];                //replace the currently highest value of the heap with the last number inserted whcih in the spot one less than the current size because of how the arrays work
            currentSize--;                                              //both go down in size show that we are removing the largest value and to ignore that we current have two copies of the same number by making out of our range
            trickleDown(0);                                             //bring the current number in the top spot down to it's current spot by moving it through the heap
            return true;                                                //show that your actions were a success
        }
        
        public void trickleDown(int index){
            int indexChild = 0;                     //the index of the largest child between left and right.
            int left = (index*2)+1;                 //left child of the current spot
            int right = (index*2)+2;                //right child of the current number
            if(left >= currentSize){                //if the left child is larger than  current size which would either mean that the right is also higher than current size by default so that means both children do not exist and we do not continue to change the heap
                return;
            }
            else if(right < currentSize && arrayOfHeap[right] > arrayOfHeap[left]){         //if right index is less than current size which means it exists in the size limit of the array and it has a larger value 
                indexChild = right;                                                         //then the index of child is right and will be used as comparision between it and the parent
            }
            else{                                                                           //if right did not meet any of it's need requirments then the left child will the chosen one to be compared
                indexChild = left;                                                          
            }
            if(arrayOfHeap[indexChild] > arrayOfHeap[index]){                               //index child or the highest and most existant child betwen our current index is larger than the index
                int temp = arrayOfHeap[index];                                              //switch the values between index child and index parent so that index child is now in the higher postion in the heap
                arrayOfHeap[index] = arrayOfHeap[indexChild];
                arrayOfHeap[indexChild] = temp;
                trickleDown(indexChild);                                                    //recurse through the heap again to find the right position for index, which is when all children to compare to is gone
            }
        }
        
        
        public void buildArray(String s){
            int form;               //int that will represent the parsed int version of each number brought out of the string s
            if(s.lastIndexOf(",") == -1){       //if the there exists no commas in the string then we are assuming the user is using spaces the differentiate between the numbers                 
                int origStop = s.length() - s.lastIndexOf(" ");         //create stopping point by finding the last point in the string that has a space and subtracting it from the length to find the total amount of string before the ending             
                while(s.length() > origStop){                           //before the the length of the string is cut to the the stopping point                     
                    String st = s.substring(0, s.indexOf(" "));         //this string is the numbers before the first space                 
                    if(currentSize < maxSize){                          //if the array is not already full                                                
                        form = Integer.parseInt(st);                    //create a number out of the string before the first space which would be the user submitted number
                        insert(form);                                   //send it to the insert function already established by this program
                        s = s.substring(s.indexOf(" ")+1, s.length());  //cut the string before the first space, so that everything aftewords is examined and inserted          
                    }
                    else{
                        System.out.println("Overflow!");                //if the array is full, then break out                            
                        break;
                    }
                }
            }
            else{           //this is if the user decided to use commas in the program
                int origStop = s.length() - s.lastIndexOf(",");         //read the instructions for the spaces, because it does the exact same thing except with spaces
                while(s.length() > origStop){                           //Quick summary for the instructions above: find the stopping point by finding last point , ; then get the substring before the first comma, see if the array is full, if not add it,
                    String st = s.substring(0, s.indexOf(","));         //then cut the substring before the first comma along with the first comma so that the while loop can check the next comma substring
                    if(currentSize < maxSize){                          //if the array is full, then don't add. Use parse int on the substring to turn it into a integer
                        form = Integer.parseInt(st);
                        insert(form);
                        s = s.substring(s.indexOf(",")+1 ,s.length());
                    }
                    else{
                        System.out.println("Overflow!");
                        break;
                    }
                }
            }
            if(currentSize < maxSize){              //inserts the remaining integer which all that is left of string s so there is no commas or spaces to worry about and can be entered straight away
                form = Integer.parseInt(s);
                insert(form); 
            }
            else{
                System.out.println("Overflow!");
            }
        }
        
        
        public void print(){
            for(int i = 0; i < currentSize; i++){                   //use a for loop to print each individual intger in breadth first order which is already how the array is set up.
                System.out.print(arrayOfHeap[i] + " ");
            }
            System.out.println();
        }
        
        public int Search(int key){                     //simple for loop searches the whole array for the user entered key and returns if found, returns -1 if nothing is found
            for(int i = 0; i < currentSize; i++){
                if(key == arrayOfHeap[i]){
                    return i;
                }
            }
            return -1;
        }
        
    }
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);             //scanner for user submiitted options
        heap h = new heap(100);                             //creates a heap for the program that is 100 spaces long which was the requirement
        String res;                                         //string for user submissions
        String s;                                           //string for entering into the heap
        int num;
        do {
            
            System.out.println("1. Build a MAX Array: ");
            System.out.println("2. Print Heap: ");
            System.out.println("3. Insert a node: ");
            System.out.println("4. Delete the largest node:  ");
            System.out.println("5.  Search the heap for a key: ");
            System.out.println("6. Exit: ");
            System.out.println("Choose: ");
            res = input.nextLine();
            
            switch(res.charAt(0)){
                case '1':
                    System.out.println("Please enter the values you wish to help build the heap: ");
                    s = input.nextLine();
                    h.buildArray(s);
                    break;
                case '2':
                    System.out.println("The heap tree (in breadth first traversal) includes: ");
                    h.print();
                    break;        
                case '3':
                    System.out.println("Please enter the number you wish to add to the heap");
                    num = Integer.parseInt(input.nextLine());
                    h.insert(num);
                    break;
                case '4':
                    h.deleteLargest();
                    System.out.println("Largest number deleted!");
                    break;        
                case '5':
                    System.out.println("Enter the number you wish to find!");
                    num = Integer.parseInt(input.nextLine());
                    System.out.println("found the value at " + h.Search(num));
                    break;
                case '6': 
                    System.out.println("Bye!");
                    break;
                default:
                    System.out.println("invalid! try it again.");
            }
        } while (res.charAt(0) != '6');
    }
    
}
