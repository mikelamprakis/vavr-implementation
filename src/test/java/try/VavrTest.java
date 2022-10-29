import io.vavr.control.Try;
import org.junit.Test;


public class VavrTest {

    @Test
    public void plainJava_tryCatch(){
        try{
            calculateSomething(1);
        }catch(BusinessException1 e1){
            e1.printStackTrace();
        }catch (BusinessException2 e2){
            e2.printStackTrace();;
        }
    }


    @Test
    public void vavr_TryOf() throws BusinessException2, BusinessException1 {
        // result0 -> Failure(BusinessException1)
        Try<Integer> result0 = Try.of(()-> calculateSomething(1));
        System.out.println("result0 -> " + result0);

        // result1 -> Success(10)
        Try<Integer> result1 = Try.of(()-> calculateSomething(1))
                .recover(BusinessException1.class, 10)
                .recover(BusinessException2.class,20);
        System.out.println("result1 -> " + result1);

        // result2 -> Success(20)
        Try<Integer> result2 = Try.of(()-> calculateSomething(2))
                .recover(BusinessException1.class, 10)
                .recover(BusinessException2.class,20);
        System.out.println("result2 -> " + result2);

        // result3 -> Success(30)
        Try<Integer> result3 = Try.of(()-> calculateSomething(3))
                .recover(BusinessException1.class, 10)
                .recover(BusinessException2.class,20);
        System.out.println("result3 -> " + result3);

        // get & orElse - result0_orElse -> 50
        Integer result0_get_orElse = result0.getOrElse(50);
        System.out.println("result0_orElse -> " + result0_get_orElse);

        // getRecovered & or else - result1_getRecovered_orElse -> 10
        Integer result1_getRecovered_orElse = result1.getOrElse(50);
        System.out.println("result1_getRecovered_orElse -> " + result1_getRecovered_orElse);
    }


    private Integer calculateSomething(int i) throws BusinessException1, BusinessException2 {
        if (i==1){
            throw new BusinessException1();
        }
        if (i==2){
            throw new BusinessException2();
        }
        return 30;
    }

}