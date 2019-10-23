package kcn.libGDXbrowser;

public class TinyEntity
{
    public String getName()
    {
        return name;
    }

    private String name;

    private float posX;
    private float posY;

    public TinyEntity(String name, float posX, float posY)
    {
        this.name = name;
        this.posX = posX;
        this.posY = posY;
    }

    public float getPosX()
    {
        return posX;
    }

    public void setPosX(float posX)
    {
        this.posX = posX;
    }

    public float getPosY()
    {
        return posY;
    }

    public void setPosY(float posY)
    {
        this.posY = posY;
    }
}
