/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package FilaBanco;

public interface QueueTAD<E>
{
    void add(E element);
    E remove();
    int size();
    boolean isEmpty();
    void clear();
    E element();
}
