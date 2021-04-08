public class Contestant{
  public int id;
  public int points;

  public Contestant(int id, int points){
    this.id = id;
    this.points = points;
  }

  public int getId(){
    return id;
  }

  public int getPoints(){
    return points;
  }

  public void addPoints(int p){
    points += p;
  }

  public void removePoints(int p){
    points -= p;
  }

}
