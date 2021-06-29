#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <time.h>

typedef struct Element Element;
typedef struct List List;

struct Element {
    int value;
    Element* next;
    Element* prev;
};


struct List {
    int size;
    Element* head;
};

bool isEmpty(List* list) {
    if (list->size == 0) {
        return true;
    }
    return false;
}

List* createList() {
    List* list = malloc (sizeof(List));
    list->head = NULL;
    list->size = 0;
    return list;
}

void initList(List* list, int value) {
    Element* element = malloc (sizeof(Element));
    element->value = value;
    element->next = element;
    element->prev = element;
    list->head = element;
    list->size = 1;
}

void addToList(List* list, int value) {
    if(isEmpty(list)) {
        initList(list, value);
        return;
    }

    Element* element = malloc (sizeof(Element));
    element->value = value;
    element->prev = list->head->prev;
    element->next = list->head;
    list->head->prev->next = element;
    list->head->prev = element;
    list->size++;
}

void addToListByIndex(List* list, int index, int value) {
    if (index > list->size) {
        printf("Wyjście poza listę!\n");
        return;
    }

    Element* element = malloc(sizeof (Element));
    element->value = value;
    Element* tmp = list->head;

    if (index == 0) {
        element->next = tmp;
        element->prev = tmp->prev;
        tmp->prev->next = element;
        tmp->prev = element;
        list->head = element;
    } else {
        for (int i = 1; i < index; i++) {
            tmp = tmp->next;
        }
        element->next = tmp->next;
        element->prev = tmp;
        tmp->next->prev = element;
        tmp->next = element;
    }
    list->size++;
}

bool deleteFromList(List* list, int index) {
    if (index >= list->size) {
        return false;
    }


    Element* tmp = list->head;

    if (index == 0) {
        tmp->next->prev = tmp->prev;
        tmp->prev->next = tmp->next;
        list->head = tmp->next;
        free(tmp);
        list->size--;
        return true;
    }

    if (index < list->size / 2) {
        for (int i = 0; i <= index; i++) {
            tmp = tmp->next;
        }
    } else {
        int start = list->size - 1;
        for (int i = start; i >= index; i--) {
            tmp = tmp->prev;
        }
    }

    tmp->next->prev = tmp->prev;
    tmp->prev->next = tmp->next;
    free(tmp);
    list->size--;
    return true;
}

bool check(List* list, int value) {
    Element* tmp = list->head;

    for (int i = 0; i < list->size; i++) {
        if (tmp->value == value) {
            return true;
        }
        tmp = tmp->next;
    }

    return false;
}

int find(List* list, int index) {
    if (index >= list->size)  {
        return -1;
    }
    Element* tmp = list->head;
    if (index < list->size / 2) {
        for (int i = 0; i <= index; i++) {
            tmp = tmp->next;
        }
    } else {
        int start = list->size - 1;
        for (int i = start; i >= index; i--) {
            tmp = tmp->prev;
        }
    }

    return tmp->value;
}

void showList(List* list) {
    if (isEmpty(list)) {
        printf("TO JEST PUSTE ");
        return;
    }

    Element* tmp = list->head;
    int end = list->size;
    printf("(H) ");
    for (int i = 0; i < end; i++) {
        printf("%d -> ", tmp->value);
        tmp = tmp->next;
    }
    printf("(H)\n");
}

List* merge(List* list1, List* list2) {
    if (isEmpty(list1) && !isEmpty(list2)) {
        return list2;
    } else if (isEmpty(list2)) {
        return list1;
    }

    List* merged = createList();
    Element* tmp = list1->head;
    int end = list1->size;

    for (int i = 0; i < end; i++) {
        addToList(merged, tmp->value);
        tmp = tmp->next;
    }

    tmp = list2->head;
    end = list2->size;

    for (int i = 0; i < end; i++) {
        addToList(merged, tmp->value);
        tmp = tmp->next;
    }

    return merged;
}

int main() {
    List* list0 = createList();
    addToList(list0, 11);
    showList(list0);
    addToListByIndex(list0, 0, 12);
    showList(list0);
    addToListByIndex(list0, 2, 13);
    showList(list0);
    deleteFromList(list0, 2);
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
    addToList (list1, 15);
    showList (list1);
    addToList (list1, 17);
    showList (list1);
    addToList (list1, 18);
    showList (list1);
    printf ("Check if value 12 is in list : %d\n", check(list1, 12));
    printf ("Find value in element of index 1 :%d\n", find(list1, 1));
    printf ("Find value in element of index 4 :%d\n", find(list1, 4));
    deleteFromList(list1, 0);
    printf("\n");
    printf("---List 1---\n");
    showList (list1);

    List* list2 = createList ();
    addToList (list2, 198);
    addToList (list2, 76);
    addToList (list2, 99);
    printf("\n");
    printf("---List 2---\n");
    showList(list2);

    List* merged = merge (list1, list2);
    printf("\n");
    printf("---Result---\n");
    showList (merged);
    printf("\n");

    
    int value;
    int index;
    double executionTime;
    clock_t startTime;
    clock_t endTime;

    List* test = createList();
    srand(time(NULL));
    for (int i = 0; i < 1000; i++) {
        value = rand() % 1900;
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
    printf("\n");
    printf ("Checking element %d everytime lasts %f15.\n", index, executionTime);


    int* randomIndex = malloc (n * sizeof (int));
    int sume = 0;

    for (int i = 0; i < n; i++) {
        randomIndex[i] = rand() % (test -> size);
        if (randomIndex[i] < test->size / 2) {
            sume +=randomIndex[i];
        } else {
            sume+=(test->size - randomIndex[i]);
        }
    }

    startTime = clock();
    for (int i = 0; i < n; i++) {
        find (test, randomIndex[i]);
    }
    endTime = clock();

    executionTime = (double) (endTime - startTime) / (CLOCKS_PER_SEC * n);
    int result = sume / n;
    printf("\n");
    printf ("Checking random elements lasts %f15.\n", executionTime);
    printf("Average number of steps: %d\n", result);

    free (randomIndex);
    return 0;
    
}