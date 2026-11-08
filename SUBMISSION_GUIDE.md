# Submission guide

## 1. Edit your details (one place only)
Open `app/src/main/res/values/strings.xml` and change the 5 lines at the top:
dev_name, dev_student_no, dev_group, dev_programme, app_url (your GitHub repo URL).

## 2. Run in Android Studio
File > Open > select the BaggageFeeCalculator folder > wait for Gradle sync > Run on emulator/phone.

## 3. Upload to GitHub
1. Create a public repo on github.com named BaggageFeeCalculator.
2. In the project folder: 
   git init; git add .; git commit -m "ICT602 assignment"; 
   git branch -M main; git remote add origin https://github.com/yourusername/BaggageFeeCalculator.git; git push -u origin main
3. Put the same URL in strings.xml (app_url) and in UFuture.

## 4. YouTube video script (marks are given from the video!)
Say each point out loud and show it on screen:
1. Intro: name, student no, group, programme (10 s).
2. INPUT (6): show 3 inputs. Weight = text field, Rate = text field, Ticket class = RadioGroup (a different widget, needed for full marks).
3. OUTPUT (6): run the 4 sample cases from the brief (25 eco @15 -> RM81.00, 18 eco @15 -> RM0.00, 55 biz @12 -> RM194.40, 42.5 biz @12 -> RM32.40). Point out the 3 outputs are formatted (2 decimals, "kg", "RM").
4. CUSTOMIZATION (6): show custom colour theme, app name in title bar, custom suitcase icon on the home screen/app drawer.
5. ABOUT PAGE (6): menu > About, show details, copyright, tap the URL to open the GitHub page. Show the Share button in the toolbar too.
6. GOOD DESIGN (6): show instructions card, helper text under fields, allowance hint, error messages (leave fields empty, tap Calculate), the "within allowance" notice.
7. Show the code briefly on GitHub / Android Studio.

## 5. UFuture
Submit: name + student no, GitHub URL, YouTube URL.
