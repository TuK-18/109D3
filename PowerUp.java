import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PowerUp extends GameObject {
    private String type;       // Loại power-up: "expand", "multi-ball", "slow", ...
    private boolean active;    // Đã được kích hoạt hay chưa
    private double fallSpeed; // Tốc độ rơi

    public PowerUp(Rectangle shape, String type) {
        super(shape);
        this.type = type;
        this.active = false;
        this.fallSpeed = 2.0;

        shape.setFill(Color.GOLD); // màu quà tặng
        shape.setWidth(20);
        shape.setHeight(20);
    }

    public void fall() {
        this.setY(this.getY() + fallSpeed);
        System.out.println("PowerUp falling at Y = " + this.getY());
    }

    public void activate(Platform platform, java.util.ArrayList<Ball> balls) {
        switch (type) {
            case "expand":
                platform.setW(platform.getW() + 50);
                break;
            case "multi-ball":
                Ball newBall = new Ball(new javafx.scene.shape.Circle(platform.getX(), platform.getY(), 10, Color.ORANGE));
                balls.add(newBall);
                break;
            case "slow":
                for (Ball b : balls) {
                    b.vSpeed.mult(0.5);
                }
                break;
            // thêm các loại khác nếu cần
        }
        this.active = true;
    }

    public String getType() {
        return type;
    }

    public boolean isActive() {
        return active;
    }
}