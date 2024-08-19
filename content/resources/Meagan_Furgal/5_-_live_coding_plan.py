class Node:
    def __init__(self, value):
        self.value = value
        self.children = []

    def addChild(self, value):
        self.children.append(Node(value))

    def findChild(self, value):
        found = None
        for child in self.children:
            if child.value == value:
                found = child
        return found

    def removeChild(self, value):
        child = self.findChild(value)
        if child:
            self.children = self.children + child.children
            self.children.remove(child)


class Tree:
    def __init__(self, value):
        self.root = Node(value)


"""
Examples
t1 = Tree(1)
t1.root.addChild(2)
t1.root.addChild(3)
t1.root.children[0].addChild(4)
t1.root.children[0].addChild(5)
t1.root.children[1].addChild(6)
t1.root.children[1].addChild(7)
t1.root.children[1].addChild(8)

print(t1.root) # prints object address
print(t1.root.value)
for child in t1.root.children:
    print(child.value)

t1.root.removeChild(3)
for child in t1.root.children:
    print(child.value) # node 3 children are now children of root
"""









