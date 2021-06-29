package main

import "fmt"

type Element struct {
	value int
	next  *Element
}

type Stack struct {
	top  *Element
	size int
}

func createStack() *Stack {
	return &Stack{top: nil, size: 0}
}

func isEmpty(s *Stack) bool {
	return s.size == 0
}

func initStack(s *Stack, val int) {
	e := &Element{value: val, next: nil}
	s.top = e
	s.size = 1
}

func push(s *Stack, val int) {

	if isEmpty(s) {
		initStack(s, val)
		return
	}

	e := &Element{value: val, next: s.top}
	s.top = e
	s.size++
}

func pop(s *Stack) bool {
	if isEmpty(s) {
		fmt.Println("Stos pusty!")
		return false
	}

	tmp := s.top.next
	s.top = tmp 
	s.size--
	return true
}

func print(s *Stack) {
	if isEmpty(s) {
		fmt.Println("Stack is empty!!!")
	} else {
		fmt.Println(" ")
		fmt.Println("--------------------")
		fmt.Println(" ")
		curr := s.top
		for curr != nil {
			fmt.Println(curr.value)
			fmt.Println("↓")
			curr = curr.next
		}
		fmt.Println("NIL")
		fmt.Println(" ")
		fmt.Println("--------------------")
		fmt.Println(" ")

	}

}


func main() {
	stack := createStack()
	pop(stack)
	push(stack, 3)
	push(stack, 13)
	push(stack, 21)
	push(stack, 37)
	push(stack, 2115)
	print(stack)
	pop(stack)
	print(stack)
}