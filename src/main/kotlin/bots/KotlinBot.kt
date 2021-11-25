package bots

import com.vdurmont.emoji.EmojiParser
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll
import org.telegram.telegrambots.meta.api.methods.send.SendAudio
import org.telegram.telegrambots.meta.api.methods.send.SendDocument
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendVideo

import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import java.io.File

class KotlinBot : TelegramLongPollingBot() {

    override fun getBotUsername(): String {
        //return bot username
        // If bot username is @HelloKotlinBot, it must return
        return "HelloKotlinBot"
    }

    override fun getBotToken(): String {
        // Return bot token from BotFather
        return "2136356801:AAHl4fak1Jea5wTYLPyc8T2VBuOOJ7Te_aA"
    }

    override fun onUpdateReceived(update: Update?) {
        // We check if the update has a message and the message has text
        val nameSender = update?.message?.from?.firstName
        val chatId = update?.message?.chatId.toString()
        val messageCommand = update?.message?.text
        val callBackData = update?.callbackQuery?.data

        try {
            if(messageCommand=="/start") {
                val welcome = """
                    Olá $nameSender, tudo bem? Espero que sim :smile:
                    
                    *Bora testar os comandos a seguir?*
                    
                    /start \-  Mensagem inicial
                    /spider \- Qual o melhor homem aranha?
                    /treta \- Qual seu lado em guerra civil??
                    /music \-  Musica pra relaxar
                    /video \-  Coisa de caiçara
                    /text \-   Marckdown
                """.trimIndent()

                val sendDocument = SendDocument().apply {
                    this.chatId = chatId
                    caption = EmojiParser.parseToUnicode(welcome)
                    document = InputFile("https://media.giphy.com/media/xT9DPIlGnuHpr2yObu/giphy.gif")
                    parseMode = "MarkdownV2"
                }

                execute(sendDocument)
            }

            if(messageCommand == "/spider") {
                val sendPoll = SendPoll().apply {
                    this.chatId = chatId
                    question = "Qual o melhor homem aranha?"
                    options = listOf("Tobey Maguire", "Tom Holland ", "Andrew Garfield" )
                    type = "quiz"
                    correctOptionId = 0
                    isAnonymous = false
                }

                execute(sendPoll)
            }


            if(messageCommand == "/treta") {
                val buttons = listOf(
                    listOf(
                        InlineKeyboardButton().apply {
                            text = "Capitão America"
                            callbackData = "/cap"
                        },
                        InlineKeyboardButton().apply {
                            text = "Homem de ferro"
                            callbackData = "/iron"
                        }
                    )
                )

                val options = InlineKeyboardMarkup().apply {
                    keyboard = buttons
                }

                val sendDocument = SendDocument().apply {
                    this.chatId = chatId
                    document = InputFile("https://media.giphy.com/media/vBjLa5DQwwxbi/giphy.gif")
                    caption = "Qual foi seu lado em guerra civil?"
                    replyMarkup = options
                }

                execute(sendDocument)
            }

            if(callBackData == "/cap"){
                val callBackChatId = update.callbackQuery?.message?.chatId.toString()

                val sendDocument = SendDocument().apply {
                    this.chatId = callBackChatId
                    caption = "Team CAP"
                    document = InputFile("https://media.giphy.com/media/l3V0bC7GReFMkM6Na/giphy.gif")
                }

                execute(sendDocument)
            }
            if(callBackData == "/iron"){
                val callBackChatId = update.callbackQuery?.message?.chatId.toString()

                val sendDocument = SendDocument().apply {
                    this.chatId = callBackChatId
                    caption = "Team IRON"
                    document = InputFile("https://media.giphy.com/media/26AHyWQgMGEGoy3jG/giphy.gif")
                }

                execute(sendDocument)
            }

            if(messageCommand == "/music"){

                val sendAudio = SendAudio().apply {
                    this.chatId = chatId
                    audio = InputFile(File("E:\\_KOTLIN\\ebac\\src\\main\\resources\\maispedida.mp3"))
                    title = "A mais pedida - Raimundos"
                }
                execute(sendAudio)
            }

            if(messageCommand == "/video"){
                val sendVideo = SendVideo().apply {
                    this.chatId = chatId
                    caption = "um video pegando pregaiu"
                    thumb = InputFile(File("E:\\_KOTLIN\\ebac\\src\\main\\resources\\thumb.JPG"))
                    video = InputFile(File("E:\\_KOTLIN\\ebac\\src\\main\\resources\\mergulho.mp4"))
                }

                execute(sendVideo)
            }

            if(messageCommand == "/text"){
                val markdownV2Text = """
                    UTILIZANDO MARKDOWN:
                    
                    *bold \*text*
                    _italic \*text_
                    __underline__
                    ~strikethrough~
                    *bold _italic bold ~italic bold strikethrough~ __underline italic bold___ bold*
                    [inline URL](http://www.example.com/)
                    [inline mention of a user](tg://user?id=123456789)
                    `inline fixed-width code`
                    ```kotlin
                    fun main() {
                        println("Hello Kotlin!")
                    }
                    ```
                """.trimIndent()

                val sendMessage = SendMessage().apply {
                    this.chatId = chatId
                    text = markdownV2Text
                    parseMode = "MarkDownV2"

                }
                execute(sendMessage)
            }

//            else {
//                val sendDocument = SendMessage().apply {
//                    this.chatId = chatId
//                    text = EmojiParser.parseToUnicode("não funcionou :disappointed:")
//                    parseMode = "MarkdownV2"
//                }
//
//                execute(sendDocument)
//            }
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
    }
}