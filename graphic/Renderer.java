package graphic;

//package org.example;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Renderer {
    private Canvas canvas;
    private GraphicsContext gc;

    // Constructor nhận Canvas từ Main
    public Renderer(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
    }

    // Clear canvas với màu trắng
    public void clear() {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    // Vẽ hình chữ nhật
    public void drawRectangle(double x, double y, double width, double height) {
        gc.setFill(Color.RED);
        gc.fillRect(x, y, width, height);
    }

    // Vẽ hình tròn
    public void drawCircle(double centerX, double centerY, double radius, Color color) {
        gc.setFill(color);
        gc.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
    }

    // Load và vẽ ảnh
    public void drawImage(String imagePath, double x, double y, double width, double height) {
        try {
            Image image = new Image(imagePath);
            gc.drawImage(image, x, y, width, height);
        } catch (Exception e) {
            System.out.println("Không thể load ảnh: " + imagePath);
        }
    }
}
