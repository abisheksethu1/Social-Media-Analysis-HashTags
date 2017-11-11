import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class hashtagcounter  {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			BufferedReader reader = null;
			Heap f=null;
            String fileName = args[0];
            List<HeapNode> tp = new ArrayList<HeapNode>();
            File file = new File(fileName);
            File file2 = new File("output_file.txt");
            try {
            	while(true){
					if(file2.createNewFile()){
						break;
					}
					else{
						file2.delete();
					}

				}
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
            PrintWriter writer = null;
			try {
				writer = new PrintWriter(file2, "UTF-8");
			} catch (FileNotFoundException | UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            try {
            	reader = new BufferedReader(new FileReader(file));
            	String line = null;
            	int x=0;
            	while ((line = reader.readLine()) != null) {
            		if(line.startsWith("#")){
            			String word = line.substring(1);
                		String[] s = word.split(" ");
                		if(x==0){
                			f = new Heap(s[0],Integer.parseInt(s[1]));
                			x=1;
                		}
                		else{
                			f.insert(s[0], Integer.parseInt(s[1]));
                		}

            		}
            		else{
            			if(line.contains("STOP")||line.contains("stop")){
            				break;
            			}
            			HeapNode h;
            			
            			for(int i=0;i<(Integer.parseInt(line));i++){
            				if(f.noOfNodes==0){
            					break;
            				}
            				h = f.removeMax();
            				try{            				    
            				    if(i==(Integer.parseInt(line))-1){
            				    	writer.println(h.word);
            				    }
            				    else{
            				    	writer.print(h.word+",");
            				    }
            				    
            				} catch (Exception e) {
            				   // do something
            					
            				}
            				tp.add(h);
            			}
            			for(int i=0;i<(Integer.parseInt(line));i++){
            				h = tp.get(i);
            				f.insert(h.word, h.frequency);
            			}
            			tp.clear();
            		}

            	}
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                }
                
            }
            writer.close();
            
	}

}
