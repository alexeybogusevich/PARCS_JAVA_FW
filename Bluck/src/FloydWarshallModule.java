import parcs.*;

public class FloydWarshallModule implements AM {
    private int number;
    private int[][] chunk;

    public void run(AMInfo info) {
        number = info.parent.readInt();
        System.out.println("Current number is " + number);
        chunk = (int[][])info.parent.readObject();

        int n = chunk[0].length;
        int c = chunk.length;

        for (int k = 0; k < n; k++)
        {
            int[] currentRow;

            if (k >= number * c && k < number*c + c)
            {
                currentRow = chunk[k % c];
                info.parent.write(chunk[k % c]);
            }
            else
            {
                currentRow = (int[])info.parent.readObject();
            }

            for (int i = 0; i < c; i++)
            {
                for (int j = 0; j < n; j++)
                {
                    chunk[i][j] = MinWeight(chunk[i][j], chunk[i][k], currentRow[j]);
                }
            }
        }

        info.parent.write(chunk);
        System.out.println("Done!");
    }

    static int MinWeight(int a, int b, int c)
    {
        if (a != Integer.MAX_VALUE)
        {
            if (b != Integer.MAX_VALUE && c != Integer.MAX_VALUE)
                return Integer.min(a, b + c);
            else
                return a;
        }
        else
        {
            if (b == Integer.MAX_VALUE || c == Integer.MAX_VALUE)
                return a;
            else
                return b + c;
        }
    }
}
