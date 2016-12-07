package Lab3Kaltenyte;

/**
 * @author Monika Kaltenyte
 */
import java.util.HashSet;
import laborai.gui.MyException;
import java.util.ResourceBundle;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;

public class GreitaveikosTyrimas {

    TreeSet<Integer> tree = new TreeSet<>();
    HashSet<Integer> hash = new HashSet<>();
    public static final String FINISH_COMMAND = "finish";
    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("laborai.gui.messages");

    private static final String[] TYRIMU_VARDAI = {"add T", "add H", "contains T", "contains H"};
    private static final int[] TIRIAMI_KIEKIAI = {3,5,45,99,78,555,77};
    private static final int k=5;
    private final BlockingQueue resultsLogger = new SynchronousQueue();
    private final Semaphore semaphore = new Semaphore(-1);
    private final Timekeeper tk;
    private final String[] errors;


    public GreitaveikosTyrimas() {
        
        semaphore.release();
        tk = new Timekeeper(TIRIAMI_KIEKIAI, resultsLogger, semaphore);
        errors = new String[]{
            MESSAGES.getString("error1"),
            MESSAGES.getString("error2"),
            MESSAGES.getString("error3"),
            MESSAGES.getString("error4")
        };
    }

    public void pradetiTyrima() {
        try {
            SisteminisTyrimas();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    public void SisteminisTyrimas() throws InterruptedException {
       int x=3;
        try {
         
            for (int k : TIRIAMI_KIEKIAI) {
               
                tree.clear();
                hash.clear();
                tk.startAfterPause();
                tk.start();
                for (int a : TIRIAMI_KIEKIAI) {
                    tree.add(a);
                }
                tk.finish(TYRIMU_VARDAI[0]);
                for (int a : TIRIAMI_KIEKIAI) {
                    hash.add(a);
                }
                tk.finish(TYRIMU_VARDAI[1]);
                for (int a : TIRIAMI_KIEKIAI) {
                    tree.contains(a);
                }
                tk.finish(TYRIMU_VARDAI[2]);
                for (int a : TIRIAMI_KIEKIAI) {
                  hash.contains(a);
                }
                tk.finish(TYRIMU_VARDAI[3]);
                tk.seriesFinish();
            }
            tk.logResult(FINISH_COMMAND);
        } catch (MyException e) {
            if (e.getCode() >= 0 && e.getCode() <= 3) {
                tk.logResult(errors[e.getCode()] + ": " + e.getMessage());
            } else if (e.getCode() == 4) {
                tk.logResult(MESSAGES.getString("msg3"));
            } else {
                tk.logResult(e.getMessage());
            }
        }
    }

    public BlockingQueue<String> getResultsLogger() {
        return resultsLogger;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

}

