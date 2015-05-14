package com.nikitaend.polproject.adapter.holder;

/**
 * Holders contain information for adapters
 * from adapters we getItem and type of these items - holder type
 * 
 * curtain holder - items for menu left curtain 
 * left image and right title
 * xml screen card curtain
 * @author Endaltsev Nikita
 *         start at 12.05.15.
 */
public class CurtainHolder {
    
    public int resourceOfImage;
    public int titleOfSection;
    
    public CurtainHolder(int resourceOfImage, int titleOfSection) {
        this.resourceOfImage = resourceOfImage;
        this.titleOfSection = titleOfSection;
    }
}
