import parcs.*;

import java.io.File;
import java.util.Scanner;

public class FloydWarshallMainModule implements AM {

    private static int NUM_DAEMONS = 2;

    private static channel[] channels;
    private static point[] points;
    private static int[][] matrix;

    public static void main(String[] args) throws Exception {
        try {
            task curtask = new task();
            curtask.addJarFile("FloydWarshallMainModule.jar");
            matrix = readData(curtask.findFile("input"));

            AMInfo info = new AMInfo(curtask, null);
            point p = info.createPoint();
            channel c = p.createChannel();
            p.execute("FloydWarshallMainModule");
            c.write(matrix);

            System.out.println("Waiting for result...");
            System.out.println("Result: " + c.readLong());
            curtask.end();
        }catch(Exception ex)
        {
            System.out.println(ex.getStackTrace());
        }
    }

    public void run(AMInfo info) {
        channels = new channel[NUM_DAEMONS];
        points = new point[NUM_DAEMONS];

        if (matrix.length % NUM_DAEMONS != 0)
        {
            System.out.println(String.format("Matrix size (now u1=%s) should be divided by u2=%s!", matrix.length, NUM_DAEMONS));
            return;
        }

        for (int i = 0; i < NUM_DAEMONS; ++i)
        {
            points[i] = info.createPoint();
            channels[i] = points[i].createChannel();
            points[i].execute("FloydWarshallModule");
        }

        distributeData();
        parallelFloyd();
        int[][] result = gatherData();

        print(result);
        System.out.println("Done");
        System.out.println();
    }

    private int[][] gatherData()
    {
        int chunkSize = matrix.length / NUM_DAEMONS;
        int[][] result = new int[matrix.length][];

        for (int i = 0; i < channels.length; i++)
        {
            int[][] chunk = (int[][])channels[i].readObject();
            for (int j = 0; j < chunkSize; j++)
            {
                result[i * chunkSize + j] = chunk[j];
            }
        }

        return result;
    }

    private void parallelFloyd()
    {
        Object locker = new Object();
        int chunkSize = matrix.length / NUM_DAEMONS;
        for (int k = 0; k < matrix.length; k++)
        {
            synchronized (locker)
            {
                int currentSupplier = k / chunkSize;
                int[] currentRow = (int[])channels[currentSupplier].readObject();

                for (int ch = 0; ch < channels.length; ch++)
                {
                    if (ch != currentSupplier)
                    {
                        channels[ch].write(currentRow);
                    }
                }
            }
        }
    }

    private void distributeData()
    {
        for (int i = 0; i < channels.length; i++)
        {
            System.out.println("Sent to channel: " + i);
            channels[i].write(i);
            int chunkSize = matrix.length / NUM_DAEMONS;

            int[][] chunk = new int[chunkSize][];

            for (int j = 0; j < chunkSize; j++)
            {
                chunk[j] = matrix[i * chunkSize + j];
            }

            channels[i].write(chunk);
        }
    }

    private static void print(int[][] ans) {
        for (int j = 0; j < ans.length; j++) {
            for (int k = 0; k < ans.length; k++) {
                System.out.println(ans[j][k]);
            }
            System.out.println();
        }
    }

    private static int[][] readData(String filename) throws Exception {
        Scanner sc = new Scanner(new File(filename));
        int n = sc.nextInt();
        int a[][] = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = sc.nextInt();
            }
        }
        return a;
    }
}
