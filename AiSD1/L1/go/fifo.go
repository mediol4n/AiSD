package main

import "fmt"

type Element struct {
	value int
	prev  *Element
}

type Queue struct {
	head *Element
	tail *Element
	size int
}

func createQueue() *Queue {
	queue := &Queue{head: nil, tail: nil, size: 0}
	return queue
}

func isEmpty(q *Queue) bool {
	return q.size == 0
}

func initQueue(q *Queue, val int) {
	element := &Element{value: val, prev: nil}
	q.head = element
	q.tail = element
	q.size = 1
}

func insert(q *Queue, val int) {
	if isEmpty(q) {
		initQueue(q, val)
	}

	e := &Element{prev: nil, value: val}
	q.tail.prev = e
	q.tail = e
	q.size++
}

func delete(q *Queue) bool {
	if isEmpty(q) {
		return false
	}

	tmp := q.head.prev
	q.head = tmp
	q.size--
	return true
}

func print(q *Queue) {
	if isEmpty(q) {
		fmt.Println("Queue is empty")
		return
	}


	tmp := q.head
	for tmp != nil {
		fmt.Print(tmp.value, "<- ")
		tmp = tmp.prev
	}

	fmt.Println("NIL")
}



func main() {
	queue := createQueue()
	insert(queue, 16)
	insert(queue, 54)
    insert(queue, 98)
    insert(queue, 17)
	print(queue)
	delete(queue)
	print(queue)
	delete(queue)
	print(queue)
	delete(queue)
	print(queue)
	delete(queue)
}
