package be.kuleuven;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

public class CheckNeighboursInGrid{
    public CheckNeighboursInGrid() {

    }

    /**
     * This method takes a 1D Iterable and an element in the array and gives back an iterable containing the indexes of all neighbours with the same value as the specified element
     *@return - Returns a 1D Iterable of ints, the Integers represent the indexes of all neighbours with the same value as the specified element on index 'indexToCheck'.
     *@param grid - This is a 1D Iterable containing all elements of the grid. The elements are integers.
     *@param width - Specifies the width of the grid.
     *@param height - Specifies the height of the grid (extra for checking if 1D grid is complete given the specified width)
     *@param indexToCheck - Specifies the index of the element which neighbours that need to be checked
     */

    public static Iterable<Integer> getSameNeighboursIds(Iterable<Integer> grid,int width, int height, int indexToCheck){
        /*
        for x,y:
        x-1,y-1   x,y-1   x+1,y+1
        x-1,y     x,y     x+1,y
        x-1,y+1   x,y+1   x+1,y+1
         */
        ArrayList<Integer> result = new ArrayList<>();

        int valueOfIndex = getValueFromIndex(grid, indexToCheck);
        int[] location = getCoordinate(width, height, indexToCheck);

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++){
                int[] neighbourLocation = Arrays.copyOf(location, location.length);
                neighbourLocation[0] = neighbourLocation[0] + x;
                neighbourLocation[1] = neighbourLocation[1] + y;
                try {
                    int neighbourIndex = getIndex(width, height, neighbourLocation);
                    if (indexToCheck != neighbourIndex) {
                        if (getValueFromIndex(grid, neighbourIndex) == valueOfIndex) {
                            result.add(neighbourIndex);
                        }
                    }
                } catch (IndexOutOfBoundsException ignored){
                }

            }
        }
        Collections.sort(result);
        return result;
    }

    /**
     *@param maxWidth - Specifies the width of the grid.
     *@param maxHeight - Specifies the height of the grid (extra for checking if 1D grid is complete given the specified width)
     *@param indexToCheck - Specifies the index of the element which neighbours that need to be checked
     * @return - An array of type [width, height]
     */
    public static int[] getCoordinate(int maxWidth, int maxHeight, int indexToCheck) throws IndexOutOfBoundsException{
        int[] result = new int[2];
        result[0] = indexToCheck%(maxWidth);                  //width
        result[1] = (indexToCheck - result[0])/(maxWidth);  //height
        if(result[1] < maxHeight){
            return result;
        }
        throw new IndexOutOfBoundsException();
    }

    /**
     *@param maxWidth - Specifies the width of the grid.
     *@param maxHeight - Specifies the height of the grid (extra for checking if 1D grid is complete given the specified width)
     *@param coordinates - Specifies the coordinate of the element; of type [width, heigth]
     * @return - the index of said element
     */
    public static int getIndex(int maxWidth, int maxHeight, int[] coordinates) throws IndexOutOfBoundsException{
        if (coordinates[0] >= 0 && coordinates[1] >= 0 && coordinates[0] < maxWidth && coordinates[1] < maxHeight) {
            return coordinates[0] + coordinates[1] * maxWidth;
        }
        throw new IndexOutOfBoundsException();
    }

    /**
     * @param grid - This is a 1D Iterable containing all elements of the grid. The elements are integers.
     * @param indexToCheck - Specifies the index of the element which neighbours that need to be checked
     * @return  The value of the element with the given index
     */
    public static int getValueFromIndex(Iterable<Integer> grid, int indexToCheck){
        int valueOfIndex = -1;
        Iterator<Integer> gridIterator = grid.iterator();
        for (int i = 0; gridIterator.hasNext(); i++) {
            int currentValue = gridIterator.next();
            if(i == indexToCheck){
                valueOfIndex = currentValue;
                break;
            }
        }
        return valueOfIndex;
    }
}
