package main

import (
	"fmt"
)


type Element struct {
	value int
	next  *Element
}

type List struct {
	head *Element
	tail *Element
	size int
}

func isEmpty(l *List) bool {
	return l.size == 0
}

func createList() *List {
	return &List{head: nil, tail: nil, size: 0}
}

func initList(l *List, val int) {
	e := &Element{next: nil, value: val}
	l.head = e
	l.tail = e
	l.size = 1
}

func insert(l *List, val int) {
	if isEmpty(l) {
		initList(l, val)
		return
	}
	e := &Element{value: val, next: nil}
	l.head.next = e
	l.head = e
	l.size++
}

func insertByIndex(l *List, index int, val int) {

	if index > l.size {
		fmt.Println("Wyjście poza listę!!!")
		return
	}


	e := &Element{value: val, next: nil}
	tmp := l.tail

	if index == 0 {
		e.next = tmp
		l.tail = e
	} else {
		for i:= 1; i < index; i ++ { tmp = tmp.next }
		e.next = tmp.next
		tmp.next = e
	}
	l.size++
}


func delete(l *List, index int) bool {
	if index >= l.size { return false }

	tmp := l.tail
	var prev *Element = nil

	if index == 0 {
		l.tail = l.tail.next
	} else {
		for i:= 1; i <= index; i++ {
			prev = tmp
			tmp = tmp.next
		}

		prev.next = tmp.next
	}

	l.size--
	return true
}

func check(l *List, val int) bool {
	tmp := l.tail
	for tmp != nil {
		 if tmp.value == val {
			 return true
		 } else {
			 tmp = tmp.next
		 }
	}

	return false
}


func find(l *List, index int) int {
	if index >= l.size { return -1 }
	
	tmp := l.tail
	for i := 1; i <= index; i++ {
		tmp = tmp.next
	}

	return tmp.value
}


func print(l *List) {
	if isEmpty(l) { fmt.Println("Empty!"); return }

	tmp := l.tail
	for tmp != nil {
		fmt.Print(tmp.value, "-> ")
	}

	fmt.Print("NULLL\n")
}

func main() {
	//todo 
}
