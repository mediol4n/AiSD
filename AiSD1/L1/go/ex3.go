package main

type Element struct {
	value int
	next  *Element
	prev  *Element
}

type List struct {
	size int
	head *Element
}

func isEmpty(l *List) bool {
	return l.size == 0
}

func createList() *List {
	return &List{head: nil, size: 0}
}

func initList(l *List, val int) {
	e := &Element{next: nil, value: val, prev: nil}
	e.next = e
	e.prev = e
	l.head = e
	l.size = 1
}

func insert(l *List, val int) {
	if isEmpty(l) {
		initList(l, val)
		return
	}
	e := &Element{value: val, next: l.head, prev: l.head.prev}
	l.head.prev.next = e
	l.head.prev = e
	l.size++
}

func remove(l *List, index int) bool {
	if index >= l.size { return false }

	tmp := l.head

	if index == 0 {
		tmp.next.prev = tmp.prev
		tmp.prev.next = tmp.next
		l.head = tmp.next
		l.size--
		return true
	}

	if index < l.size/2 {
		for i := 0; i <= index; i++ {
			tmp = tmp.next
		}
	} else {
		start := l.size -1
		for i := start; i >= index; i-- {
			tmp = tmp.prev
		}
	}

	tmp.next.prev = tmp.prev
	tmp.prev.next = tmp.next
	l.size--
	return true
}

func check(l *List, val int) bool {
	tmp := l.head
	for i := 0; i < l.size; i++ {
		if tmp.value == val {
			return true
		}
		tmp = tmp.next
	}

	return false
}

func find(l *List, index int) int {

	if index >= l.size || index < 0 {return -1}

	tmp := l.head
	if index < l.size/2 {
		for i := 0; i <= index; i++ {
			tmp = tmp.next
		}
	} else {
		for start := l.size -1; start >= index; start-- {
			tmp = tmp.prev
		}
	}

	return tmp.value
}

func showList(l *List) {
	if isEmpty(l) {return}

	tmp := l.head
	end := l.size
	print("(H) ")
	for i:=0; i < end; i++ {
		print(tmp.value, " ->")
		tmp = tmp.next
	}

	println("(H)")
}


func merge(l1 *List, l2 *List) *List {

	if isEmpty(l1) {
		return l2
	}

	if isEmpty(l2) {
		return l1
	}

	merged := createList()
	tmp := l1.head
	end := l1.size

	for i := 0; i < end; i++ {
		insert(merged,tmp.value)
		tmp = tmp.next
	}

	tmp = l2.head
	end = l2.size

	for i := 0; i < end; i++ {
		insert(merged, tmp.value)
		tmp = tmp.next
	}

	return merged
}


func main() {
	//todo
}
