########################
# ICS4U                
# Assignment 2         
# December 15th, 2023  
# Ethan Hayes          
# This is a program where the user is a game developer working for a company that comesup with game ideas. 
########################

# Personal dictionary class
class GameDeveloper:
    def __init__(self, user_name): # Adding self, users name, dictionary, recently added words, and catrgories 
        self.user_name = user_name
        self.dictionary = {}
        self.recently_added_words = []
        self.word_categories = ('Games', 'Activities', 'Sports')

# Greeting function to the user
    def greeting(self): 
        print(f"\n''' ''' \nWelcome to the game developers computer, {self.user_name}! We're excited to have you here!")

# Main menu function that displays all of the users options to them
    def display_menu(self): 
        print("***Main Selection***\n1: Add a new game idea\n2: Search a game idea\n3: Display all game ideas\n4: Exit\n")

# Function to add games to the dictionary 
    def add_word(self): 
        word = input("Enter the name of the game idea: ").lower()
        if word in self.dictionary: # If the word they add has already been added to the dictionary
            print(f"The game '{word}' already exists in the game list.")
        else: # If the Word can be added to the dict. it asks for the meaning and category of the word. 
            meaning = input(f"Enter the characters of the game:'{word}': ")
            category = input("Enter the theme of the game('Winter', 'Summer', 'Action', 'Puzzle', etc...): ")
            self.dictionary[word] = {'meaning': meaning, 'category': category}
            self.recently_added_words.append(word) # Adding the word to the recently added words list
            print(f"Game '{word}' added successfully!")

# Function to search games in the dictionary
    def search_word(self):
        word = input("Enter a game to search: ").lower() # Asking for the users word to add
        if word in self.dictionary: # If statement for when the word is found in the dictionary it tells user the meaning and category of the word
            print(f"Game: {word}\nCharacters: {self.dictionary[word]['meaning']}") 
            if 'category' in self.dictionary[word]:
                print(f"Category: {self.dictionary[word]['category']}")
        else: # Else statement for if the word is not found in te dictionary
            print(f"The game '{word}' is not found in the dictionary.")

# Function to display all games in the dictionary
    def display_all_words(self): 
        sorted_words = sorted(self.dictionary.keys(), key=lambda x: x.lower()) # Lambda function sorting the words
        for word in sorted_words: # For statement for all the words in the dictionary as well as their categories and meanings to be printed to user
            print(f"\nGame: {word}\nCharacters: {self.dictionary[word]['meaning']}")
            if 'category' in self.dictionary[word]:
                print(f"Category: {self.dictionary[word]['category']}")
        if not sorted_words: # If statement for if there isnt any words added to the dictionary yet
            print("The list of games is empty.")

# Function for the users choices during the program
    def run(self): 
        self.greeting()
        while True: # While statement to keep you in the loop each time until you exit
            self.display_menu()
            choice = input("\nEnter your choice: ") # Users choice for wahat to do next
            print("''' '''")
            if choice == '1': # User chose to add a new word
                self.add_word()
            elif choice == '2': # User chose to search a word 
                self.search_word()
            elif choice == '3': # User chose to display all the words
                self.display_all_words()
            elif choice == '4': # User chose to exit the personal dictionary
                print("Thank you for using the Game Developers Computer. Goodbye!")
                break
            else: # Users choice wasnt valid
                print("Invalid choice. Please enter a valid option.")

# Main program
if __name__ == "__main__":  
    user_name = input("Enter your name: ") # Asking users name before they start
    my_dictionary = GameDeveloper(user_name)
    my_dictionary.run() # Running main program 