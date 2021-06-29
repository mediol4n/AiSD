#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include <time.h>

typedef struct Element Element;
typedef struct Queue Queue;

struct Element {
    int value;
    Element* prev;
};

struct Queue {
    Element* head;
    Element* tail;
    int size;
};

Queue* createQueue () {
    Queue* queue = malloc (sizeof (Queue));
    queue -> head = NULL;
    queue -> tail = NULL;
    queue -> size = 0;
    return queue;
}

bool isEmpty (Queue* queue) {
    if (queue -> size == 0) {
        return true;
    } else {
        return false;
    }
}

void initQueue (Queue* queue, int value) {
    Element* element = malloc (sizeof (Element));
    element -> value = value;
    element -> prev = NULL;
    queue -> head = element;
    queue -> tail = element;
    queue -> size = 1;
}

void addToQueue (Queue* queue, int value) {

    if (isEmpty(queue)) {
        initQueue (queue, value);
        return; 
    }

    Element* element = malloc (sizeof (Element));
    element -> value = value;
    element -> prev = NULL;

    queue -> tail -> prev = element;
    queue -> tail = element;
    queue -> size++;
}

bool takeFromQueue (Queue* queue) {
    if (isEmpty(queue)) {
        return false;
    }

    Element* tmp = queue -> head -> prev;
    free (queue -> head);
    queue -> head = tmp;
    queue -> size--;
    return true;
}

void showQueue (Queue* queue) {
    if (isEmpty(queue)) {
        printf("EMPTY!\n");
        return;
    }
    Element* tmp;
    tmp = queue -> head;
    while (tmp != NULL) {
        printf ("%d <- ", tmp -> value);
        tmp = tmp -> prev;
    }
    printf ("NULL\n");
}

int main () {
    Queue* queue = createQueue();
    addToQueue (queue, 16);
    addToQueue (queue, 54);
    addToQueue (queue, 98);
    addToQueue (queue, 17);
    showQueue(queue);
    takeFromQueue (queue);
    showQueue(queue);
    takeFromQueue (queue);
    showQueue(queue);
    takeFromQueue (queue);
    showQueue(queue);
    takeFromQueue (queue);
    showQueue(queue);

    int value;
    Queue* test = createQueue();
    srand(time(NULL));
    for (int i = 0; i < 100; i++) {
        value = rand() % 100;
        addToQueue (test, value);
    }
    showQueue(test);
    for (int i = 0; i < 10; i++) {
        takeFromQueue(test);
        printf("\n");
        showQueue(test);
        
    }

    return 0;
}