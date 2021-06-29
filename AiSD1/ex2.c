#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <time.h>

typedef struct Element Element;
typedef struct List List;

struct Element {
    int value;
    Element* next;
};

struct List {
    Element* head;
    Element* tail;
    int size;
};

bool isEmpty (List* list) {
    if (list -> size == 0) {
        return true;
    } else {
        return false;
    }
}

List* createList () {
    List* list = malloc (sizeof (List));
    list -> head = NULL;
    list -> tail = NULL;
    list -> size = 0;
    return list;
}

void initList (List* list, int value) {
    Element* element = malloc (sizeof (Element));
    element -> value = value;
    element -> next = NULL;
    list -> head = element;
    list -> tail = element;
    list -> size = 1;
}

void addToList (List* list, int value) {
    if (isEmpty (list)) {
        initList (list, value);
        return;
    }

    Element* element = malloc (sizeof (Element));
    element -> value = value;
    element -> next = NULL;
    list -> head -> next = element;
    list -> head = element;
    list -> size++;
}

void addToListByIndex(List* list, int index, int value) {
    if (index > list->size) {
        printf("Wyjście poza listę!\n");
        return;
    }

    Element* element = malloc(sizeof (Element));
    element->value = value;
    Element* tmp = list->tail;

    if (index == 0) {
        element->next = tmp;
        list->tail = element;
    } else {
        for (int i = 1; i < index; i++) {
            tmp = tmp->next;
        }
        element->next = tmp->next;
        tmp->next = element;
    }
    list->size++;

    
}

bool takeFromList (List* list, int index) {
    if (index >= list -> size) {
        return false;
    }

    Element* tmp = list -> tail;
    Element* prev = NULL;

    if (index == 0) {
        list -> tail = list -> tail -> next;
    } else {
        for (int i = 1; i <= index; i++) {
            prev = tmp;
            tmp = tmp -> next;
        }
        //prev is (index-1) element, tmp is index element
        prev -> next = tmp -> next;
    }
    free (tmp);
    list -> size--;
    return true;
}

bool check (List* list, int value) {
    Element* tmp = list -> tail;
    while (tmp != NULL) {
        if (tmp -> value == value) {
            return true;
        } else {
            tmp = tmp -> next;
        }
    }
    return false;
}

int find (List* list, int index) {
    if (index >= list -> size) {
        return -1;
    }
    
    Element* tmp = list -> tail;
    for (int i = 1; i <= index; i++) {
        tmp = tmp -> next;
    }
    return tmp -> value;
}

void showList (List* list) {
    if (isEmpty (list)) {
        printf ("EMPTY!\n");
        return;
    }
    
    Element* tmp = list -> tail;
    while (tmp != NULL) {
        printf ("%d -> ", tmp -> value);
        tmp = tmp -> next;
    }
    printf ("NULL\n");
}

//Merging two lists.
List* megre (List* list1, List* list2) {
    List* merged = createList();
    Element* tmp = list1 -> tail;
    while (tmp != NULL) {
        addToList (merged, tmp -> value);
        tmp = tmp -> next;
    }

    tmp = list2 -> tail;
    while (tmp != NULL) {
        addToList (merged, tmp -> value);
        tmp = tmp -> next;
    }

    return merged;
}

int main () {
    List* list0 = createList();
    addToList(list0, 11);
    showList(list0);
    addToListByIndex(list0, 0, 12);
    showList(list0);
    addToListByIndex(list0, 2, 13);
    showList(list0);
    takeFromList(list0, 2);
    showList(list0);
    printf("\n");
    printf("\n");

    List* list1 = createList();
    addToList (list1, 10);
    showList (list1);
    addToList (list1, 11);
    showList (list1);
    addToList (list1, 12);
    showList (list1);
    printf ("Check if value 12 is in list : %d\n", check(list1, 12));
    printf ("Find value in element of index 0 : %d\n", find(list1, 0));
    takeFromList(list1, 0);
    printf("\n");
    printf("---List 1---\n");
    showList (list1);

    List* list2 = createList();
    addToList (list2, 16);
    addToList (list2, 18);
    addToList (list2, 87);
    printf("\n");
    printf("---List 2---\n");
    showList (list2);

    List* merged = megre (list1, list2);
    printf("\n");
    printf("---Merged---\n");
    showList (merged);


    int value;
    int index;
    double executionTime;
    clock_t startTime;
    clock_t endTime;
    List* test = createList ();

    srand(time(NULL));
    for (int i = 0; i < 1000; i++) {
        value = rand() % 2137;
        addToList (test, value);
    }

    index = rand() % test -> size - 1;
    int n = 10000;

    startTime = clock();
    for (int i = 0; i < n; i++) { 
        find (test, index);
    }
    endTime = clock();

    executionTime = (double) (endTime - startTime) / (CLOCKS_PER_SEC * n);
    printf ("Checking element %d everytime lasts %f15.\n", index, executionTime);
    int* randomIndex = malloc (n * sizeof (int));
    int sume = 0;
    for (int i = 0; i < n; i++) {
        randomIndex[i] = rand() % (test -> size);
        sume += randomIndex[i];
    }

    startTime = clock();
    for (int i = 0; i < n; i++) {
        find (test, randomIndex[i]);
    }
    endTime = clock();
    int result = sume / n;
    executionTime = (double) (endTime - startTime) / (CLOCKS_PER_SEC * n);
    printf ("Checking random elements lasts %f15.\n", executionTime);
    printf("Average index: %d\n", result);
    free (randomIndex);
    return 0;
}