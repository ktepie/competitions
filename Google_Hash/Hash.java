import java.util.HashMap;
import java.io.File;
import java.util.Scanner;


class Book{
  public int id;
  public int score;
  Book(final int id_, final int score_){
    id = id_;
    score = score_;
  }

}

class Library{
  public int id;
  public int time;
  public int capacity;
  public Book[] books;

  public Library(int id_, int time_, int capacity_, Book[] books_){
    id = id_;
    time = time_;
    capacity = capacity_;
    books = books_.clone();
  }

}

class Hash{
  static HashMap<Integer,Integer> AllBooks = new HashMap<Integer,Integer>();
  static HashMap<Integer,Book> ScannedBooks = new HashMap<Integer,Book>();

  public static void printf(final Library temp){
    System.out.println(temp.id + " " + temp.books.length);
    if(temp.books.length == 0) return;
    System.out.print(temp.books[0].id);
    for(int i=1;i<temp.books.length;++i){
      System.out.print(" "+temp.books[i].id);
    }
    System.out.println();
  }

  public static double calc(final Library temp){
    int score=0;
    Book[] bks = temp.books.clone();
    for(int i=0;i<bks.length;++i){
      int id = bks[i].id;
      if(ScannedBooks.get(id)==null){
        score += bks[i].score;
      }
    }
    return score;
  }

  public static double fcalc(final Library temp){
    final double cap = (double) temp.capacity;
    final double tim = (double) temp.time;
    final double days = (double) temp.books.length;
    final double optime = tim + (days/cap);
    double result = (calc(temp) - tim)/optime;
    result = result*result;
    return result;
  }

  public static void scanThem(final Library temp){
    for(int i=0;i<temp.books.length;++i){
      Book tbook = temp.books[i];
      ScannedBooks.put(tbook.id,tbook);
    }

  }

  public static Book[] inLib(String line_, int books_){
    Scanner sc = new Scanner(line_);
    Book[] temp = new Book[books_];
    int i = 0;
    int id = 0 ;
    int score = 0;
    while(sc.hasNextLine()){
      id = Integer.parseInt(sc.next());
      score = AllBooks.get(id);
      Book x = new Book(id,score);
      temp[i] = x;
      i++;
    }
    return temp;
  }


  public static void main(final String[] args) throws Exception{
    final String file1 = "a_example.txt";
    final String file2 = "b_read_on.txt";
    final String file3 = "c_incunabula.txt";
    final String file4 = "d_tough_choices.txt";
    final String file5 = "e_so_many_books.txt";
    final String file6 = "f_libraries_of_the_world.txt";
    File file = new File(file4);
    Scanner sc = new Scanner(file);

    HashMap<Integer,Library> Libraries = new HashMap<Integer,Library>();
    HashMap<Integer,Library> result = new HashMap<Integer,Library>();


    int NBook  = Integer.parseInt(sc.next());
    int NLibrary = Integer.parseInt(sc.next());
    int Ndays = Integer.parseInt(sc.next());
    int temp_id = 0;
    int temp_Score;
    sc.nextLine();
    String line = sc.nextLine();
    Scanner s1 = new Scanner(line);

    while(s1.hasNext()){
      temp_Score = Integer.parseInt(s1.next());
      AllBooks.put(temp_id,temp_Score);
      temp_id++;
    }
    int id_ =0;
    int time_;
    int capacity_;
    int books_;

    while(sc.hasNextLine()){
      if(sc.hasNext()){
        books_ = Integer.parseInt(sc.next());
        time_ = Integer.parseInt(sc.next());
        capacity_ = Integer.parseInt(sc.next());
        sc.nextLine();
        String line_ = sc.nextLine();
        Book[] temp = new Book[books_];

        int pi = 0;
        int pid;
        int pscore;
        Scanner sch = new Scanner(line_);
        while(sch.hasNextLine()){
          pid = Integer.parseInt(sch.next());
          pscore = AllBooks.get(pid);
          Book x = new Book(pid,pscore);
          temp[pi] = x;
          pi++;
        }

        Library lib = new Library(id_,time_,capacity_,temp);
        Libraries.put(id_,lib);
        id_++;
        System.out.println();
        if(id_==NLibrary) break;
      }
    }

    double scoreMin;
    double scoreMax;
    double score;
    for(int i: Libraries.keySet()){
      Library temp = Libraries.get(i);
      scoreMax = fcalc(temp);
      score = scoreMax;
      for(int j: Libraries.keySet()){
        if(i!=j){
          Library lib = Libraries.get(j);
          scoreMin = fcalc(lib);
          if(scoreMin>scoreMax){
            result.put(lib.id,lib);
            scanThem(lib);
          }
        }
      }
      if(scoreMax==score){
        result.put(temp.id,temp);
        scanThem(temp);
      }
    }

    System.out.println(result.size());
    for(int i: result.keySet()){
      Library temp = result.get(i);
      printf(temp);
    }




  }
}
