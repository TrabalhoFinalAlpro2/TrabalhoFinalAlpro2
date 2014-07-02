package FilaBanco;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListArray<E> implements ListTAD<E> {

    private static final int INITIAL_SIZE = 10;
    private E[] data;
    private int count;
    private int capacity;

    /**
     * Construtor da lista
     */
    public ListArray() {
        this(10);
    }

    /**
     * Construtor da lista
     */
    public ListArray(int c) {
        if (c > 0) {
            capacity = c;
        } else {
            capacity = INITIAL_SIZE;
        }
        data = (E[]) new Object[capacity];
        count = 0;
    }

    /**
     * Esvazia a lista
     */
    @Override
    public void clear() {
        data = (E[]) new Object[INITIAL_SIZE];
        count = 0;
        capacity = INITIAL_SIZE;
    }

    /**
     * Retorna true se a lista não contêm elementos
     *
     * @return true se a lista não contêm elementos
     */
    @Override
    public boolean isEmpty() {
        return (count == 0);
    }

    /**
     * Retorna o número de elementos da lista
     *
     * @return o número de elementos da lista
     */
    @Override
    public int size() {
        return count;
    }

    /**
     * Adiciona um elemento ao final da lista
     *
     * @param element elemento a ser adicionado ao final da lista
     */
    @Override
    public void add(E element) {
        if (count == capacity) {
            setCapacity(capacity * 2);
        }
        data[count] = element;
        count++;
    }

    /**
     * Insere um elemento em uma determinada posição da lista
     *
     * @param index a posição da lista onde o elemento será inserido
     * @param element elemento a ser inserido
     * @throws IndexOutOfBoundsException se (index < 0 || index > size())
     */
    @Override
    public void add(int index, E element) {
        if ((index < 0) || (index > count)) {
            throw new IndexOutOfBoundsException("Index = " + index);
        }
        if (count == capacity) {
            setCapacity(capacity * 2);
        }
        for (int i = count - 1; i >= index; i--) {
            data[i + 1] = data[i];
        }
        data[index] = element;
        count++;
    }

    /**
     * Remove o elemento de uma determinada posição da lista
     *
     * @param index a posição da lista
     * @return o elemento que foi removido da lista
     * @throws IndexOutOfBoundsException se (index < 0 || index >= size())
     */
    @Override
    public E remove(int index) {
        if ((index < 0) || (index >= count)) {
            throw new IndexOutOfBoundsException("Index = " + index);
        }
        E item = data[index];
        for (int i = index; i < count - 1; i++) //deslocar item uma posiÃ§Ã£o para esquerda
        {
            data[i] = data[i + 1];
        }
        count--;
        data[count] = null;
        return item;
    }

    /**
     * Remove a primeira ocorrência do elemento na lista, se estiver presente
     *
     * @param element o elemento a ser removido
     * @return true se a lista contém o elemento especificado
     */
    @Override
    public boolean remove(E element) {
        for (int i = 0; i < count; i++) {
            if (data[i].equals(element)) {
                for (int j = i; j < count - 1; j++) { //deslocar item uma posicao para esquerda
                    data[j] = data[j + 1];
                }
                count--;
                data[count] = null;
                return true;
            }
        }
        return false;
    }

    /**
     * Retorna o elemento de uma determinada posição da lista
     *
     * @param index a posição da lista
     * @return o elemento da posição especificada
     * @throws IndexOutOfBoundsException se (index < 0 || index >= size())
     */
    @Override
    public E get(int index) {
        if ((index < 0) || (index >= count)) {
            throw new IndexOutOfBoundsException("Index = " + index);
        }
        return data[index];
    }

    /**
     * Substitui o elemento armanzenado em uma determinada posição da lista pelo
     * elemento indicado
     *
     * @param index a posição da lista
     * @param element o elemento a ser armazenado na lista
     * @return o elemento armazenado anteriormente na posição da lista
     * @throws IndexOutOfBoundsException se (index < 0 || index >= size())
     */
    @Override
    public E set(int index, E element) {
        if ((index < 0) || (index >= count)) {
            throw new IndexOutOfBoundsException("Index = " + index);
        }
        E item = data[index];
        data[index] = element;
        return item;
    }

    /**
     * Retorna true se a lista contém o elemento especificado
     *
     * @param element o elemento a ser testado
     * @return true se a lista contém o elemento especificado
     */
    @Override
    public boolean contains(E element) {
        int target = 0;
        boolean achou = false;
        while ((target < count) && (!achou)) {
            if (data[target].equals(element)) {
                achou = true;
            } else {
                target++;
            }
        }
        return achou;
    }

    /**
     * Retorna o índice da primeira ocorrência do elemento na lista, ou -1 se a
     * lista não contém o elemento
     *
     * @param element o elemento a ser buscado
     * @return o índice da primeira ocorrência do elemento na lista, ou -1 se a
     * lista não contém o elemento
     */
    @Override
    public int indexOf(E element) {
        // Procura elemento no array, se achar retorna
        for (int i = 0; i < count; i++) {
            if (data[i].equals(element)) {
                return i;
            }
        }
        // Neste ponto, não achou: retorna -1
        return -1;
    }

    public Iterator<E> iteratorBackToFront() {
        return (new Iterator<E>() {
            private int currentPosition = count - 1;
            @Override
            public boolean hasNext() {
                if (currentPosition < 0) {
                    return false;
                } else {
                    return true;
                }
            }
            @Override
            public E next() {
                if (currentPosition < 0) {
                    throw new NoSuchElementException();
                }
                E o = data[currentPosition];
                currentPosition--;
                return o;
            }
            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }

    @Override
    public Iterator<E> iterator() {
        return (new Iterator<E>() {

            private int currentPosition = 0;

            @Override
            public boolean hasNext() {
                if (currentPosition < count) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public E next() {
                if (currentPosition == count) {
                    throw new NoSuchElementException();
                }
                // guarda o elemento da posicao corrente
                E o = data[currentPosition];
                // incrementa a posicao corrente (passa para a proxima)
                currentPosition++;
                // retorna o elemento guardado
                return o;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("[");
        for (int i = 0; i < count; i++) {
            s.append(data[i].toString());
            if (i < count - 1) {
                s.append(",");
            }
        }
        s.append("]");
        return s.toString();
    }

    private void setCapacity(int newCapacity) {
        if (newCapacity != capacity) {
            int min = 0;
            E[] newData = (E[]) new Object[newCapacity];
            if (capacity < newCapacity) {
                min = capacity;
            } else {
                min = newCapacity;
            }
            for (int i = 0; i < min; i++) {
                newData[i] = data[i];
            }
            data = newData;
            capacity = newCapacity;
        }
    }
}
