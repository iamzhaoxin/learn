public class test {
    a x=new a();

}
class A{
    public void run(){
        System.out.println("A");
    }
}
class a extends A{
    @Override
    public void run(){
        System.out.println("a");
    }
}
