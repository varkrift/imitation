package app;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    private GraphicsContext context;
    private static long nextSecond = System.currentTimeMillis() + 1000;
    private static int framesInLastSecond = 0;
    private static int framesInCurrentSecond;

    private final int width = 100;
    private final int height = 100;
    private int[][] grids = new int[width][height];
    private int[][] future = new int[width][height];
    private int neighbours;
    private int bad_neighbours;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("GOL");
        Group root = new Group();
        Canvas canvas = new Canvas(width * 10, height * 10);
        root.getChildren().add(canvas);
        context = canvas.getGraphicsContext2D();
        stage.setScene(new Scene(root, width * 10, height * 10));

        // init
        initArray();

        // show
        //showArray();

        // init
        generate();

        // show
        showArray();

        // update
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                clear();
                render();
                showGrids();
                delay();
                showFPS();
            }
        }.start();
        stage.show();
    }

    private void render() {
        neighbours = 0;
        bad_neighbours = 0;
        future = new int[width][height];

        for (int i = 1; i < width-1; i++) {
            for (int j = 1; j < height-1; j++) {
                neighbours = 0;
                bad_neighbours = 0;
                for (int l=-1; l<=1; l++)
                    for (int m=-1; m<=1; m++) {
                        if (grids[i + l][j + m] != 0) neighbours += 1;
                        if (grids[i + l][j + m] == 2) bad_neighbours += 1;
                    }
                if (grids[i][j] != 0) neighbours -= 1;

                // rules of neighbours
                if (grids[i][j] == 1 && neighbours < 2) future[i][j] = 0;
                else if (grids[i][j] == 1 && neighbours > 3) future[i][j] = 0;
                else if(grids[i][j] == 0 && neighbours == 3) future[i][j] = 1;
                else future[i][j] = grids[i][j];

                // rules of bad neighbours
                if (grids[i][j] == 2 && bad_neighbours > 3) future[i][j] = 0;
                else if (grids[i][j] == 1 && bad_neighbours > 0) future[i][j] = 2;
            }
        }
        grids = future;
    }

    private void delay() {
        try {
            int millis = 200;
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void clear() {
        context.clearRect(0, 0, width * 10, height * 10);
    }

    private void initArray() {
        for (int i=0; i<100; i++) {
            for (int j=0; j<100; j++) {
                grids[i][j] = 0;
            }
        }
    }

    private void showArray() {
        for (int i=0; i<100; i++) {
            for (int j=0; j<100; j++) {
                System.out.print(grids[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void showGrids() {
        context.setFill(Color.CADETBLUE);
        for (int i=0; i<100; i++) {
            for (int j=0; j<100; j++) {
                if (grids[i][j] == 1) {
                    context.setFill(Color.CADETBLUE);
                    context.fillRect(i * 10, j * 10, 10, 10);
                } else if (grids[i][j] == 2) {
                    context.setFill(Color.RED);
                    context.fillRect(i * 10, j * 10, 10, 10);
                }
            }
        }
    }

    private void generate() {
        int x;
        int y;

        // generate people
        for (int i=0; i<2000; i++) {
            x = ((int) (Math.random() * width));
            y = ((int) (Math.random() * height));
            if (grids[x][y] == 0) {
                grids[x][y] = 1;
            } else --i;
        }

        // generate virus
        for (int i=0; i<100; i++) {
            x = ((int) (Math.random() * width));
            y = ((int) (Math.random() * height));
            if (grids[x][y] == 1) {
                grids[x][y] = 2;
            } else --i;
        }
    }

    void showFPS() {
        if (System.currentTimeMillis() > nextSecond) {
            nextSecond += 1000;
            framesInLastSecond = framesInCurrentSecond;
            framesInCurrentSecond = 0;
        }
        framesInCurrentSecond++;
        context.fillText("FPS: " + framesInLastSecond, 10, 20);
    }
}
