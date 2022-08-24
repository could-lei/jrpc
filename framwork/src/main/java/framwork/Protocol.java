package framwork;

/**
 * Created by next on 2022/8/23.
 */
public interface Protocol {
    void start(URL url);
    String send(URL url, Invocation invocation);
}
