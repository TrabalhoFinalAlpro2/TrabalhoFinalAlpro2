
package FilaBanco;

import java.util.EmptyStackException;

/**
 * Faça uma classe chamada StackArray<E> que implemente a interface 
 * StackTAD<E> e possua como atributo um objeto ListArray<E>. 
 * Utilize genéricos na sua implementação e lembre de gerar as 
 * exceções necessárias.
 * @author Isabel
 * @param <E> 
 */
public class StackArray<E> implements StackTAD<E> {
    
    ListArray<E> pilha;

    public StackArray() {
        pilha = new ListArray<>();
    }
    
    @Override
    public int size() {
        return pilha.size();
    }

    @Override
    public boolean isEmpty() {
        return pilha.isEmpty();
    }

    @Override
    public E top() throws EmptyStackException {
        if (pilha.isEmpty())
            throw new EmptyStackException();
        return pilha.get(pilha.size()-1);
    }

    @Override
    public void push(E element) {
        pilha.add(element);
    }

    @Override
    public E pop() throws EmptyStackException {
        if (pilha.isEmpty())
            throw new EmptyStackException();
        return pilha.remove(pilha.size()-1);
    }

    @Override
    public void clear() {
        pilha.clear();
    }
    
}
