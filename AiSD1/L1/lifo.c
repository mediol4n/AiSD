#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>

typedef struct Element Element;
typedef struct Stack Stack;

struct Element {
    int value;
    Element* next;
};

struct Stack {
    Element* top;
    int size;
};

Stack* createStack() {
    Stack* stack = malloc (sizeof (Stack));
    stack -> top = NULL;
    stack -> size = 0;
    return stack;
}

bool isEmpty(Stack* stack) {
    if (stack->size == 0) {
        return true;
    }
    return false;
}

void initStack(Stack* stack, int value) {
    Element* element = malloc (sizeof(Element));
    element->value = value;
    element->next = NULL;
    stack->top = element;
    stack->size = 1;
}

void push(Stack* stack, int value) {

    if(isEmpty(stack)) {
        initStack(stack, value);
    } else {
        Element *element = malloc(sizeof(Element));
        element->value = value;
        element->next = stack->top;
        stack->top = element;
        stack->size++;
    }
 
}

bool pop(Stack* stack) {
 
    if (isEmpty(stack)) {
        printf("EMPTY!\n");
        return false;
	} else {
        Element* tmp = stack->top->next;
        free(stack->top);
        stack->top = tmp;
        stack->size--;
        return true;
	}
 
}

void show(Stack* stack) {
    if (isEmpty(stack)) {
         printf("Stack is empty\n");
    }
    else {
        Element* current = stack->top;
        while (current != NULL) {
            printf("%i", current->value);
            printf("\n");
            printf("↓");
            printf("\n");
            current = current->next;
         }
    }
}



int main() {
        Stack* stack = createStack();
        pop(stack);
        push(stack, 3);
        push(stack, 13);
        push(stack, 21);
        push(stack, 37);
        push(stack, 2115);
        show(stack);
        pop(stack);
        printf("\n");
        show(stack);
}